package com.baszczyk.mercdream.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    lateinit var historyViewModel: HistoryViewModel
    lateinit var binding: FragmentHistoryBinding
    var isPiggyHistory = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHistoryBinding>(inflater,
        R.layout.fragment_history, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val historyModelFactory = HistoryViewModelFactory(dataSource, application)

        historyViewModel = ViewModelProviders.of(this, historyModelFactory).get(HistoryViewModel::class.java)

        binding.historyViewModel = historyViewModel

        val adapter = DepositAdapter()
        binding.piggyHistory.adapter = adapter

        historyViewModel.deposits.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.intent?.putExtra("isPiggy", false)
    }

    override fun onResume() {
        super.onResume()

        val args = activity?.intent?.extras?.get("isPiggy").toString().toBoolean()
        isPiggyHistory = args

        // OptionsMenu
        if(isPiggyHistory) {
            val piggyId = activity?.intent?.extras?.get("piggyId")
            historyViewModel.piggyDeposits(piggyId.toString().toLong())
        } else if(!isPiggyHistory) {
            //DrawerMenu
            val userId = activity?.intent?.extras?.get("id").toString().toLong()
            historyViewModel.allDeposits(userId)
        }
    }

}

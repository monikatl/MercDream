package com.baszczyk.mercdream.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.databinding.FragmentListBinding
import com.baszczyk.mercdream.logging.LoggingFragment
import kotlinx.android.synthetic.main.activity_main.*


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao

        val viewModelFactory = ListViewModelFactory(dataSource, application)

        val listViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(ListViewModel::class.java)

        binding.listViewModel = listViewModel

        val userId = activity?.intent?.extras?.get("id").toString().toLong()


        val adapter = PiggyBankAdapter(PiggyBankListener { piggyId ->
            listViewModel.onPiggyBankClicked(piggyId)
        })
        binding.piggyList.adapter = adapter


        listViewModel.allPiggies(userId)
        fabButtonActive()

        Handler().postDelayed({
            if (listViewModel.piggies.value?.isEmpty()!!) {
                binding.nonePiggies.visibility = View.VISIBLE

                binding.addNewPiggyButton.setOnClickListener { view: View ->
                    view.findNavController()
                        .navigate(ListFragmentDirections.actionListFragmentToFormFragment())
                }
            } else {

                val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                binding.piggyList.layoutManager = manager

                listViewModel.piggies.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })

                binding.lifecycleOwner = this

                listViewModel.navigateToPiggyBankFragment.observe(this, Observer { piggy ->
                    piggy?.let {

                        this.findNavController().navigate(
                            ListFragmentDirections.actionListFragmentToPiggyBankFragment(piggy)
                        )
                        listViewModel.onPiggyBankNavigated()
                    }
                })
            }
        }, 500)

        return binding.root
    }

    private fun fabButtonActive() {
        binding.fab.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }
    }
}

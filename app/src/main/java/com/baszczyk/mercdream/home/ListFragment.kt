package com.baszczyk.mercdream.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.databinding.FragmentListBinding
import com.baszczyk.mercdream.logging.LoggingFragment


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao

        val viewModelFactory = ListViewModelFactory(dataSource, application)

        val listViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ListViewModel::class.java)

        binding.listViewModel = listViewModel


        val adapter = PiggyBankAdapter()
        binding.piggyList.adapter = adapter

        listViewModel.allPiggies(1)

        binding.fab.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_listFragment_to_formFragment)}


        //val manager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        //binding.piggyList.layoutManager = manager

            listViewModel.piggies.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.data = it
                }
            })

            binding.lifecycleOwner = this








        return binding.root
    }
}

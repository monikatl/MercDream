package com.baszczyk.mercdream.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.databinding.FragmentMoreBinding


class MoreFragment : Fragment() {

    private val viewModel: MoreViewModel by lazy {
        ViewModelProviders.of(this).get(MoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoreBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter()
        return binding.root
    }

}
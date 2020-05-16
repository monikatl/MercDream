package com.baszczyk.mercdream.piggy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment


import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.databinding.FragmentPiggyBankBinding

class PiggyBankFragment : Fragment() {

    private lateinit var binding: FragmentPiggyBankBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPiggyBankBinding>(inflater,
        R.layout.fragment_piggy_bank, container, false)
        
        return binding.root
    }

}

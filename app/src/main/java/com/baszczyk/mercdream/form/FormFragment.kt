package com.baszczyk.mercdream.form


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.Mercedes
import com.baszczyk.mercdream.database.PiggyBank
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.database.User
import com.baszczyk.mercdream.databinding.FragmentFormBinding
import com.baszczyk.mercdream.logging.LoggingFragment

class FormFragment : Fragment() {

    lateinit var mercedes: Mercedes
    lateinit var piggy: PiggyBank
    lateinit var formViewModel: FormViewModel
    lateinit var binding: FragmentFormBinding
    lateinit var currentUser: User


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentFormBinding>(inflater,
            R.layout.fragment_form, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = FormViewModelFactory(dataSource, application)

        formViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FormViewModel::class.java)

        binding.formViewModel = formViewModel



        binding.nextButton.setOnClickListener { view: View ->

            createMercedes()
            formViewModel.addMercedes(mercedes)

            Handler().postDelayed({
                formViewModel.mercedesId()
                Handler().postDelayed({
                    val mercId = formViewModel.currentMercedes.value
                    piggy = PiggyBank(mercedesId = mercId!!, userId = 1, actualAmount = mercedes.price)
                    formViewModel.addPiggyBank(piggy)
                    showDialog()
                    view.findNavController().navigate(FormFragmentDirections.actionFormFragmentToPiggyBankFragment())
                }, 1000)

            }, 1000)
        }

       // binding.setLifecycleOwner(this)

        return binding.root
    }

    private fun createMercedes(){
        mercedes = Mercedes( surname = binding.surnameInput.text.toString(),
            price = binding.priceInput.text.toString().toDouble(),
            version = binding.versionInput.text.toString(),
            engineCapacity = binding.capacityInput.text.toString(),
            powerEngine = binding.powerInput.text.toString()
        )
    }

    private fun showDialog(){

        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage("Skarbonka została pomyslnie utworzona!")
            builder?.show()
    }
}

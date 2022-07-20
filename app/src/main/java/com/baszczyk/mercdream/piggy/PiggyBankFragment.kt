package com.baszczyk.mercdream.piggy

import android.content.DialogInterface

import android.media.MediaPlayer
import android.os.Bundle

import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.baszczyk.mercdream.ExtrasMessages
import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.enities.Deposit
import com.baszczyk.mercdream.database.enities.Mercedes
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.databinding.FragmentPiggyBankBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class PiggyBankFragment : Fragment() {

    private lateinit var piggyBankViewModel: PiggyBankViewModel
    private lateinit var piggy: PiggyBank
    private lateinit var mercedes: Mercedes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPiggyBankBinding>(
            inflater, R.layout.fragment_piggy_bank, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = PiggyBankViewModelFactory(dataSource, application)

        piggyBankViewModel = ViewModelProvider(this, viewModelFactory)[PiggyBankViewModel::class.java]

        binding.piggyBankViewModel = piggyBankViewModel

        val args = PiggyBankFragmentArgs.fromBundle(requireArguments())
        val piggyId = args.piggyId


        runBlocking {
            piggyBankViewModel.piggyGet(piggyId)
        }

        piggy = piggyBankViewModel.piggy.value!!

            activity?.intent?.putExtra(ExtrasMessages.PIGGY_ID, piggyId)

            runBlocking {
                piggyBankViewModel.mercedesGet(piggyBankViewModel.piggy.value?.mercedesId!!)
            }

            mercedes = piggyBankViewModel.mercedes.value!!

            binding.mercedes = mercedes
            binding.piggy = piggy

            if (piggy.actualAmount <= 0.0) {
                binding.inputAmount.visibility = View.GONE
                binding.piggyPictureDone.visibility = View.VISIBLE
            }


            binding.imageButton.setOnClickListener {
                val dateTime = getCurrentDateTime().toString()
                val deposit = binding.inputAmount.text.toString().toDouble()
                var actualAmount = piggy.actualAmount
                actualAmount -= deposit

                if (actualAmount <= 0.0) {
                    showCongratulationAlert()
                    getSound(R.raw.sound)
                    binding.piggyPictureDone.visibility = View.VISIBLE
                } else {
                    // source: salamisound.com
                    getSound(R.raw.money)
                }
                piggyBankViewModel.addDeposit(
                    Deposit(
                        amount = deposit,
                        date = dateTime,
                        mercedesId = mercedes.mercedesId,
                        userId = piggy.userId,
                        piggyName = piggy.piggyName
                    )
                )
                piggyBankViewModel.updatePiggyActualAmount(actualAmount, piggy.piggyId)

                binding.amount.text = actualAmount.toString()
                binding.inputAmount.visibility = View.GONE
                binding.piggyPicture.visibility = View.VISIBLE
                showInputToast(deposit)
                binding.piggyPicture.setOnClickListener { view: View ->
                    view.findNavController()
                        .navigate(PiggyBankFragmentDirections.actionPiggyBankFragmentSelf(piggyId))
                }
        }
            setHasOptionsMenu(true)
            return binding.root

    }


    private fun showCongratulationAlert() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setTitle("Gratualcje!!! \n Właśnie uzbierałeś na Mercedesa: ${mercedes.surname}")
        builder?.setPositiveButton("OK") { dialog, which ->
            view?.findNavController()?.navigate(R.id.action_piggyBankFragment_to_listFragment)
        }
        builder?.show()
    }

    private fun showDeleteDialog(){
        val dialog: AlertDialog.Builder? = activity?.let{
            AlertDialog.Builder(it)
        }
        dialog?.setTitle("Czy na pewno chcesz usunac Skarbonke ${piggy.piggyName}?")
        dialog?.setPositiveButton("TAK"){ dialogInterface: DialogInterface, i: Int ->
            piggyBankViewModel.deletePiggy(piggy.piggyId)
            findNavController().navigate(R.id.action_piggyBankFragment_to_listFragment)
        }
        dialog?.setNegativeButton("NIE"){ dialogInterface: DialogInterface, i: Int ->

        }
        dialog?.show()

    }

    private fun getSound(sound: Int) {
        val mediaPlayer: MediaPlayer? = MediaPlayer.create(context, sound)
        mediaPlayer?.start()
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun showInputToast(deposit: Double){
        Toast.makeText(context, "Dokonano wpłaty: $deposit PLN", Toast.LENGTH_LONG).show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.piggy_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId ){
            R.id.delete -> {showDeleteDialog()}
            R.id.historyFragment -> { activity?.intent?.putExtra(ExtrasMessages.IS_PIGGY, true)
                NavigationUI.onNavDestinationSelected(item, requireView().findNavController())}
            R.id.moreFragment -> {NavigationUI.onNavDestinationSelected(item, requireView().findNavController())}
        }
        return super.onOptionsItemSelected(item)
    }
}
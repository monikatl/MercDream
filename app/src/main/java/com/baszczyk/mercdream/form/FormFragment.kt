package com.baszczyk.mercdream.form


import android.app.*
import android.app.NotificationManager
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.enities.Mercedes
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.database.PiggyDatabase

import com.baszczyk.mercdream.databinding.FragmentFormBinding
//import com.baszczyk.mercdream.notifications.sendNotification


class FormFragment : Fragment() {

    lateinit var mercedes: Mercedes
    lateinit var piggy: PiggyBank
    lateinit var formViewModel: FormViewModel
    lateinit var binding: FragmentFormBinding
    lateinit var price: EditText

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
            this.createMercedes()
            formViewModel.addMercedes(mercedes)

            Handler().postDelayed({
                formViewModel.mercedesId()
                Handler().postDelayed({
                    createPiggy()
                    formViewModel.addPiggyBank(piggy)
                    Handler().postDelayed({
                        showDialog()
                        view.findNavController().navigate(R.id.action_formFragment_to_listFragment)
                    }, 500)

                }, 500)

            }, 500)
        }

       binding.setLifecycleOwner(this)

//        createChannel(getString(R.string.piggy_notification_chanel_id),
//            getString(R.string.piggy_notification_channel_name))
//
//        val notificationManager = ContextCompat.getSystemService(
//            app,
//            NotificationManager::class.java
//        ) as NotificationManager
//        notificationManager.sendNotification(app.getString(R.string.timer_running), app)

        return binding.root
    }

    private fun createPiggy(){
        val mercId = formViewModel.currentMercedes.value!!
        val mercSurname = mercedes.surname
        val userId = activity?.intent?.extras?.get("id").toString().toLong()
        piggy = PiggyBank(
            mercedesId = mercId,
            piggyName = mercSurname,
            userId = userId,
            actualAmount = mercedes.price
        )
    }

    private fun createMercedes(){
        mercedes = Mercedes(
            surname = binding.surnameInput.text.toString(),
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
        builder?.setTitle("Skarbonka została pomyslnie utworzona!")
        builder?.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
        }
        builder?.show()
    }

//    private fun createChannel(channelId: String, channelName: String) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(
//                channelId,
//                channelName,
//                NotificationManager.IMPORTANCE_LOW
//            )
//
//            notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.RED
//            notificationChannel.enableVibration(true)
//            notificationChannel.description = "Utworzyłes nowa Skarbonke, wrzuc cos do niej!"
//
//            val notificationManager = requireActivity().getSystemService(
//                NotificationManager::class.java
//            )
//            notificationManager.createNotificationChannel(notificationChannel)
//        }

//}
}

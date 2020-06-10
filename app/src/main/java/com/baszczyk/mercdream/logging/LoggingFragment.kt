package com.baszczyk.mercdream.logging


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.baszczyk.mercdream.ExtrasMessages
import com.baszczyk.mercdream.MainActivity

import com.baszczyk.mercdream.R

import com.baszczyk.mercdream.database.PiggyDatabase

import com.baszczyk.mercdream.databinding.FragmentLoggingBinding
import kotlinx.coroutines.runBlocking


class LoggingFragment : Fragment() {

    private lateinit var viewModel: LoggingViewModel
    private lateinit var binding: FragmentLoggingBinding
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentLoggingBinding>(inflater,
            R.layout.fragment_logging, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = LoggingViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoggingViewModel::class.java)

        //binding.loggingViewModel = viewModel

        viewModel.getAllUsersNames()

        userName = binding.userName
        userPassword = binding.userPassword

        userName.addTextChangedListener(loggingTextWatcher)
        userPassword.addTextChangedListener(loggingTextWatcher)


        binding.loggIn.setOnClickListener { view: View ->
            userName = binding.userName
            userPassword = binding.userPassword

            if(isUserInDatabase()){
                runBlocking {
                    viewModel.getUserPassword(userName.text.toString())
                }
                    if(isCorrectPassword()) {

                        runBlocking {
                            viewModel.getUser(userName.text.toString())
                        }
                            val userId = viewModel.currentUser.value!!.userId.toString()
                            val intent = Intent(activity, MainActivity::class.java).apply {
                                putExtra(ExtrasMessages.USER_ID, userId)
                            }
                            activity?.startActivity(intent)
                    } else {
                        binding.wrongData.text = "Niepoprawne hasło!"
                    }
            }else {
                view.findNavController().navigate(R.id.action_loggingFragment_to_addNewUserFragment)
            }
        }
        binding.newUser.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loggingFragment_to_addNewUserFragment)
        }
        return binding.root
    }

    private fun isUserInDatabase() =
        viewModel.users.contains(userName.text.toString())

    private fun isCorrectPassword() =
        userPassword.text.toString() == viewModel.userPassword



    private val loggingTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val name = userName.text.toString().trim()
            val password = userPassword.text.toString().trim()

            binding.loggIn.isEnabled = name.isNotEmpty() && password.isNotEmpty()
        }
    }

}

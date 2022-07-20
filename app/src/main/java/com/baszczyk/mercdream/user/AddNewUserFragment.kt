package com.baszczyk.mercdream.user

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController


import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.database.enities.User
import com.baszczyk.mercdream.databinding.AddNewUserFragmentBinding
import kotlinx.coroutines.runBlocking


class AddNewUserFragment : Fragment() {

    private lateinit var viewModel: AddNewUserViewModel
    private lateinit var binding: AddNewUserFragmentBinding
    private lateinit var newUser: EditText
    private lateinit var newPassword: EditText
    private lateinit var email: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<AddNewUserFragmentBinding>(inflater,
        R.layout.add_new_user_fragment, container, false)

        newUser = binding.newUserName
        newPassword = binding.newUserPassword
        email = binding.addUserEmail

        val application = requireNotNull(this.activity).application
        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = AddNewUserViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[AddNewUserViewModel::class.java]

        binding.addNewUserViewModel = viewModel

        newUser.addTextChangedListener(addTextWatcher)
        newPassword.addTextChangedListener(addTextWatcher)
        email.addTextChangedListener(addTextWatcher)

        binding.createUserButton.setOnClickListener { view: View ->

            newUser = binding.newUserName
            newPassword = binding.newUserPassword
            email = binding.addUserEmail

            runBlocking {
                viewModel.addNewUser(createNewUser())
                viewModel.getNewUser()
            }

                Toast.makeText(
                    this.context,
                    "Utworzono nowe konto ${viewModel.currentUser.value?.name}! Mozesz sie zalogowac!",
                    Toast.LENGTH_LONG
                ).show()

                view.findNavController().navigate(R.id.action_addNewUserFragment_to_loggingFragment)
        }
        return binding.root
    }

    private fun createNewUser(): User{
        return User(
            name = newUser.text.toString(),
            password = newPassword.text.toString(),
            email = email.text.toString()
        )
    }

    private val addTextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val name = newUser.text.toString().trim()
            val password = newPassword.text.toString().trim()
            val email = email.text.toString().trim()

            binding.createUserButton.isEnabled = (name.isNotEmpty() &&
                                                    password.isNotEmpty() &&
                                                        email.isNotEmpty())
        }

    }

}

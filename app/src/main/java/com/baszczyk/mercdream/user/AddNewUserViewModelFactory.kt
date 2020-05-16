package com.baszczyk.mercdream.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baszczyk.mercdream.database.PiggyDatabaseDao


class AddNewUserViewModelFactory (
    private val dataSource: PiggyDatabaseDao,
    private val application: Application): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(AddNewUserViewModel::class.java)){
            return AddNewUserViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
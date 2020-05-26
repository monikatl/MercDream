package com.baszczyk.mercdream.logging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import com.baszczyk.mercdream.database.enities.User
import kotlinx.coroutines.*

class LoggingViewModel (val database: PiggyDatabaseDao,
                        application: Application): AndroidViewModel(application) {


    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var currentUser = MutableLiveData<User?>()

    fun getUser(name: String){
        uiScope.launch {
            currentUser.value = getCurrentUser(name)
        }
    }

    private suspend fun getCurrentUser(name: String): User?{
        return withContext(Dispatchers.IO){
            val user = database.getUser(name)
            user
        }
    }

    lateinit var users: List<String>

    fun getAllUsersNames(){
        uiScope.launch {
            users = allUsersNames()
        }
    }
    private suspend fun allUsersNames(): List<String>{
        return withContext(Dispatchers.IO){
            var names = database.getAllUsersNames()
            names
        }
    }

    lateinit var userPassword: String

    fun getUserPassword(name: String){
        uiScope.launch {
            userPassword = getPassword(name)
        }
    }

    private suspend fun getPassword(name: String):String{
        return withContext(Dispatchers.IO){
            var password = database.getUserPassword(name)
            password
        }
    }
}
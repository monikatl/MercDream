package com.baszczyk.mercdream.logging

import android.app.Application
import android.service.autofill.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import com.baszczyk.mercdream.database.PiggyBank
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import com.baszczyk.mercdream.database.User
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


    lateinit var piggies: List<Long?>

    fun getAllPiggies(userId: Long){
        uiScope.launch {
            piggies = allPiggies(userId)
        }
    }

    private suspend fun allPiggies(userId: Long):List<Long?>{
        return withContext(Dispatchers.IO){
            val piggie = database.getPiggiesId(userId)
            piggie
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

    override fun onCleared() {
        super.onCleared()
    }
}
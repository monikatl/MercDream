package com.baszczyk.mercdream.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import com.baszczyk.mercdream.database.User
import kotlinx.coroutines.*

class AddNewUserViewModel(val database: PiggyDatabaseDao,
application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private suspend fun insertNewUser(user: User){
        withContext(Dispatchers.IO){
            database.insertNewUser(user)
        }
    }

    fun addNewUser(user: User){
        uiScope.launch {
            insertNewUser(user)
        }
    }

    var currentUser = MutableLiveData<User>()

    fun getNewUser() {
        uiScope.launch {
            currentUser.value = getCurrentUser()
        }
    }

    private suspend fun getCurrentUser(): User {
        return withContext(Dispatchers.IO){
            var user = database.getCurrentUser()
            user
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}

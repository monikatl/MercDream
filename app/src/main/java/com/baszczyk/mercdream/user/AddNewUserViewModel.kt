package com.baszczyk.mercdream.user

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import com.baszczyk.mercdream.database.enities.User
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

    suspend fun addNewUser(user: User){
            insertNewUser(user)
    }

    var currentUser = MutableLiveData<User>()

    suspend fun getNewUser() {
            currentUser.value = getCurrentUser()
    }

    private suspend fun getCurrentUser(): User {
        return withContext(Dispatchers.IO){
            var user = database.getCurrentUser()
            user
        }
    }
}

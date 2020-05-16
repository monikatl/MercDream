package com.baszczyk.mercdream.form

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercdream.database.Deposit
import com.baszczyk.mercdream.database.Mercedes
import com.baszczyk.mercdream.database.PiggyBank

import com.baszczyk.mercdream.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class FormViewModel (
    val database: PiggyDatabaseDao,
    application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

     suspend fun insertMercedes(mercedes: Mercedes){
        withContext(Dispatchers.IO) {
            database.insertMercedes(mercedes)
        }
    }

    fun addMercedes(mercedes: Mercedes){
        uiScope.launch {
            insertMercedes(mercedes)
        }
    }


    var currentMercedes = MutableLiveData<Long?>()

    private suspend fun getMercedesId(): Long?{
       return withContext(Dispatchers.IO){
            val mercedes = database.getMercedesId()
           mercedes
        }
    }

    fun mercedesId(){
        uiScope.launch {
            currentMercedes.value = getMercedesId()
        }
    }


     private suspend fun insertPiggyBank(piggyBank: PiggyBank){
        withContext(Dispatchers.IO) {
            database.insertPiggyBank(piggyBank)
        }
    }

    fun addPiggyBank(piggyBank: PiggyBank){
        uiScope.launch {
            insertPiggyBank(piggyBank)
        }
    }


}
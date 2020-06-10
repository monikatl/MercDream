package com.baszczyk.mercdream.form

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercdream.database.enities.Mercedes
import com.baszczyk.mercdream.database.enities.PiggyBank

import com.baszczyk.mercdream.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class FormViewModel (
    val database: PiggyDatabaseDao,
    application: Application) : AndroidViewModel(application){

    private suspend fun insertMercedes(mercedes: Mercedes){
        withContext(Dispatchers.IO) {
            database.insertMercedes(mercedes)
        }
    }

    suspend fun addMercedes(mercedes: Mercedes){
            insertMercedes(mercedes)
    }

    var currentMercedes = MutableLiveData<Long?>()

    private suspend fun getMercedesId(): Long?{
       return withContext(Dispatchers.IO){
            val mercedes = database.getMercedesId()
           mercedes
        }
    }

    suspend fun mercedesId(){
            currentMercedes.value = getMercedesId()
    }

     private suspend fun insertPiggyBank(piggyBank: PiggyBank){
        withContext(Dispatchers.IO) {
            database.insertPiggyBank(piggyBank)
        }
    }

    suspend fun addPiggyBank(piggyBank: PiggyBank){
            insertPiggyBank(piggyBank)
    }


}
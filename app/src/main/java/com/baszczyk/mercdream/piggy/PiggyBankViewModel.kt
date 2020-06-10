package com.baszczyk.mercdream.piggy

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercdream.database.enities.Deposit
import com.baszczyk.mercdream.database.enities.Mercedes
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class PiggyBankViewModel (dataSource: PiggyDatabaseDao, application: Application) : ViewModel(){

    val database = dataSource
    var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val piggy = MutableLiveData<PiggyBank>()

    private suspend fun getPiggy(id: Long): PiggyBank {
        return withContext(Dispatchers.IO){
            database.getPiggy(id)
        }
    }

   suspend fun piggyGet(id: Long) {
            piggy.value = getPiggy(id)
    }


    val mercedes = MutableLiveData<Mercedes>()

    private suspend fun getMercedes(id: Long): Mercedes {
        return withContext(Dispatchers.IO){
            database.getMercedes(id)
        }
    }

    suspend fun mercedesGet(id: Long){
            mercedes.value = getMercedes(id)
    }


    private suspend fun insertDeposit(deposit: Deposit){
        withContext(Dispatchers.IO){
            database.insertDeposit(deposit)
        }
    }

    fun addDeposit(deposit: Deposit){
        uiScope.launch {
            insertDeposit(deposit)
        }
    }

    private suspend fun updatePiggyBank(amount: Double, id: Long){
        withContext(Dispatchers.IO){
            database.updatePiggyBank(amount, id)
        }
    }

    fun updatePiggyActualAmount(amount: Double, id: Long){
        uiScope.launch {
            updatePiggyBank(amount, id)
        }
    }

    private suspend fun deletePiggyBank(id: Long){
        withContext(Dispatchers.IO){
            database.deletePiggy(id)
        }
    }
    fun deletePiggy(id: Long){
        uiScope.launch {
            deletePiggyBank(id)
        }
    }
}
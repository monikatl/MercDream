package com.baszczyk.mercdream.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.baszczyk.mercdream.database.enities.Deposit
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class HistoryViewModel (val database: PiggyDatabaseDao,
                        application: Application
) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    var isPiggyHistory = false

    var deposits = MutableLiveData<List<Deposit>>()

    private suspend fun getAllPiggyDeposits(id: Long): List<Deposit>{
        return withContext(Dispatchers.IO){
            val deposit = database.getAllDepositsForCurrentPiggy(id)
            deposit
        }
    }

    fun piggyDeposits(id: Long){
        uiScope.launch {
            deposits.value = getAllPiggyDeposits(id)
        }
    }

    private suspend fun getAllDeposits(id: Long): List<Deposit>{
        return withContext(Dispatchers.IO){
            val deposit = database.getAllDeposits(id)
            deposit
        }

    }

    fun allDeposits(id: Long){
        uiScope.launch {
            deposits.value = getAllDeposits(id)
        }
    }
}
package com.baszczyk.mercdream.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.database.PiggyDatabaseDao
import kotlinx.coroutines.*

class ListViewModel(
    dataSource: PiggyDatabaseDao,
    application: Application) : ViewModel(){

    val database = dataSource

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var piggies = MutableLiveData<List<PiggyBank>>()

    private suspend fun getAllPiggies(id: Long): List<PiggyBank>{
        return withContext(Dispatchers.IO){
            database.getAllPiggies(id)
        }
    }

    fun allPiggies(id: Long){
        uiScope.launch {
            piggies.value = getAllPiggies(id)
        }
    }

    private val _navigateToPiggyBankFragment = MutableLiveData<Long>()
    val navigateToPiggyBankFragment
        get() = _navigateToPiggyBankFragment

    fun onPiggyBankClicked(id: Long) {
        _navigateToPiggyBankFragment.value = id
    }

    fun onPiggyBankNavigated() {
        _navigateToPiggyBankFragment.value = null
    }


}

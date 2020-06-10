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
    var piggies = MutableLiveData<List<PiggyBank>>()

    private suspend fun getAllPiggies(id: Long): List<PiggyBank>{
        return withContext(Dispatchers.IO){
            database.getAllPiggies(id)
        }
    }

    suspend fun allPiggies(id: Long){
            piggies.value = getAllPiggies(id)
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

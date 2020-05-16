package com.baszczyk.mercdream

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class Prompt (lifecycle: Lifecycle): LifecycleObserver{

    var counter = 0

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun showBackUpToast(){
        if(counter > 0){
            Timber.i("Witaj spowrotem!")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun count(){
        counter++
    }
}
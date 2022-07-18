package com.baszczyk.mercdream

import android.app.Application
import timber.log.Timber

class MercApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
package com.xecoding.loceat

import android.app.Application
import com.xecoding.loceat.di.component.appComponent
import com.xecoding.loceat.managers.persistent.SharedPrefsManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LoceatApplication : Application() {
    private val sharedPrefsManager: SharedPrefsManager by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LoceatApplication)
            modules(appComponent)
        }
        sharedPrefsManager.setupDefaultLocation()
    }
}
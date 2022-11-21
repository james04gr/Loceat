package com.xecoding.loceat.di

import com.xecoding.loceat.managers.location.LocationManager
import com.xecoding.loceat.managers.location.LocationManagerImpl
import com.xecoding.loceat.managers.persistent.SharedPrefsManager
import com.xecoding.loceat.managers.persistent.SharedPrefsManagerImpl
import com.xecoding.loceat.model.persistent.Settings
import com.xecoding.loceat.model.persistent.SharedPrefsData
import com.xecoding.loceat.model.persistent.SharedPrefsDataImpl
import com.xecoding.loceat.repository.VenuesRepository
import com.xecoding.loceat.repository.VenuesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val testManagersModule = module {
    single<VenuesRepository> {
        return@single VenuesRepositoryImpl(get())
    }

    single<SharedPrefsData<Settings>> {
        return@single SharedPrefsDataImpl<Settings>(androidContext(), Settings.prefsName)
    }

    single<LocationManager> {
        return@single LocationManagerImpl(androidContext(), get(), get(), get())
    }

    single<SharedPrefsManager> {
        return@single SharedPrefsManagerImpl(get())
    }
}
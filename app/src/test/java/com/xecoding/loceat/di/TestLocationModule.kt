package com.xecoding.loceat.di

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import org.koin.dsl.module

val testLocationModule = module {
    single {
        return@single LocationRequest().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    single {
        return@single LocationSettingsRequest.Builder().apply {
            addLocationRequest(LocationRequest.create().apply {
                this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            })
        }
    }
}
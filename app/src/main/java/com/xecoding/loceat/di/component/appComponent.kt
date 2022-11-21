package com.xecoding.loceat.di.component

import com.xecoding.loceat.di.modules.locationModule
import com.xecoding.loceat.di.modules.managersModule
import com.xecoding.loceat.di.modules.networkModule

val appComponent = arrayListOf(managersModule, networkModule, locationModule)
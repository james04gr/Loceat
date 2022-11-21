package com.xecoding.loceat.managers.persistent

import com.xecoding.loceat.model.location.GeoCoderLocation

interface SharedPrefsManager {

    fun setupDefaultLocation()

    fun getDefaultLocation(): GeoCoderLocation

    fun storeLocation(geoCoderLocation: GeoCoderLocation)
}
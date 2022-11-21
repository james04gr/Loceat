package com.xecoding.loceat.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.GoogleMap
import com.xecoding.loceat.managers.location.LocationManager
import com.xecoding.loceat.managers.persistent.SharedPrefsManager
import com.xecoding.loceat.model.location.GeoCoderLocation
import org.koin.core.inject

class LocationViewModel() : BaseViewModel() {

    private val sharedPrefsManager: SharedPrefsManager by inject()
    private val locationManager: LocationManager by inject()
    private lateinit var testLocationManager: LocationManager
    private lateinit var testSharedPrefsManager: SharedPrefsManager

    @VisibleForTesting
    constructor(
        testLocationManager: LocationManager,
        testSharedPrefsManager: SharedPrefsManager
    ) : this() {
        this.testLocationManager = testLocationManager
        this.testSharedPrefsManager = testSharedPrefsManager
    }

    val location: LiveData<GeoCoderLocation> = locationManager.geocoderLocation

    fun saveLocation(geoCoderLocation: GeoCoderLocation) {
        sharedPrefsManager.storeLocation(geoCoderLocation)
    }

    fun applyLocationRequest() {
        locationManager.requestGeolocation()
    }

    fun setMap(googleMap: GoogleMap) {
        locationManager.setMap(googleMap)
    }

    fun showLocationSettings(
        showDialog: (exception: ResolvableApiException) -> Unit,
        showSettings: () -> Unit
    ) {
        locationManager.requestSettings(showDialog, showSettings)
    }

    @VisibleForTesting
    fun dummyLocationRequest() {
        testLocationManager.geocoderLocation
    }

    @VisibleForTesting
    fun getGeocoderLocation(): LiveData<GeoCoderLocation> {
        return testLocationManager.geocoderLocation
    }

    @VisibleForTesting
    fun saveDummyLocation(geoCoderLocation: GeoCoderLocation) {
        testSharedPrefsManager.storeLocation(geoCoderLocation)
    }
}
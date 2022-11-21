package com.xecoding.loceat.managers.location

import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockitokotlin2.verify
import com.xecoding.loceat.common.BaseManagerTest
import com.xecoding.loceat.managers.persistent.SharedPrefsManager
import com.xecoding.loceat.model.location.GeoCoderLocation
import org.junit.Test
import org.koin.core.inject
import org.mockito.Mock

class LocationManagerImplTest : BaseManagerTest() {
    @Mock
    lateinit var locationObserver: Observer<GeoCoderLocation>
    @Mock
    lateinit var map: GoogleMap

    private val sharedPrefsManager by inject<SharedPrefsManager>()
    private val locationRequest by inject<LocationRequest>()
    private val locationSettingsRequestBuilder by inject<LocationSettingsRequest.Builder>()
    private lateinit var locationManager: LocationManagerImpl

    override fun setUp() {
        super.setUp()
        locationManager = LocationManagerImpl(context, sharedPrefsManager, locationRequest, locationSettingsRequestBuilder)
    }

    @Test
    fun testGeocoderLocation() {
        locationManager.geocoderLocation.observeForever(locationObserver)
        locationManager.locationData.postValue(geoCoderLocation)
        verify(locationObserver).onChanged(geoCoderLocation)
    }
}
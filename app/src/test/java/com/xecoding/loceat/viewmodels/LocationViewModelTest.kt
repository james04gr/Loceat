package com.xecoding.loceat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.willReturn
import com.xecoding.loceat.common.BaseViewModelUnitTest
import com.xecoding.loceat.managers.location.LocationManager
import com.xecoding.loceat.model.location.GeoCoderLocation
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LocationViewModelTest : BaseViewModelUnitTest() {
    @Mock
    lateinit var locationObserver: Observer<GeoCoderLocation>

    @Mock
    lateinit var locationManager: LocationManager
    private lateinit var viewModel: LocationViewModel

    override fun setUp() {
        super.setUp()
        viewModel = LocationViewModel(locationManager, sharedPrefsManager)
    }

    @Test
    fun testFetchLocation() {
        val data: MutableLiveData<GeoCoderLocation> = MutableLiveData()
        given(locationManager.geocoderLocation).willReturn {
            data.postValue(geoCoderLocation)
            data
        }
        viewModel.getGeocoderLocation().observeForever(locationObserver)
        viewModel.dummyLocationRequest()
        verify(locationObserver).onChanged(geoCoderLocation)
    }

    @Test
    fun testStoreLocation() {
        val geoCoderLocation = GeoCoderLocation(
            LatLng(DEFAULT_LAT.toDouble(), DEFAULT_LNG.toDouble()),
            DEFAULT_ADDRESS,
            false
        )
        viewModel.saveDummyLocation(geoCoderLocation)
        val savedLocation = sharedPrefsManager.getLocation()
        assertThat(savedLocation).isNotNull()
        assertThat(savedLocation.address).isNotEmpty()
        assertThat(savedLocation.address).isEqualTo(DEFAULT_ADDRESS)
        assertThat(savedLocation.latLng).isNotNull()
        val latLng = savedLocation.latLng
        assertThat(latLng.latitude).isEqualTo(DEFAULT_LAT.toDouble())
        assertThat(latLng.longitude).isEqualTo(DEFAULT_LNG.toDouble())
    }

}
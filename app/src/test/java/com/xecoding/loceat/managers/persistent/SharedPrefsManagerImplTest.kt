package com.xecoding.loceat.managers.persistent

import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import com.xecoding.loceat.common.BaseSharedPrefsTest
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.utils.LoceatParams
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SharedPrefsManagerImplTest : BaseSharedPrefsTest() {

    @Test
    fun testGetDefaultLocation() {
        val location = sharedPrefsManager.getDefaultLocation()
        assertThat(location).isNotNull()
        val latLng = location.latLng
        assertThat(location.address).isEmpty()
        assertThat(latLng).isNotNull()
        assertThat(latLng.latitude).isEqualTo(0.0)
        assertThat(latLng.longitude).isEqualTo(0.0)
    }

    @Test
    fun testStoreLocation() {
        val customLocation = GeoCoderLocation(
            LatLng(DEFAULT_LAT.toDouble(), DEFAULT_LNG.toDouble()),
            DEFAULT_ADDRESS
        )
        sharedPrefsManager.storeLocation(customLocation)
        sharedPrefsManager.setupDefaultLocation()
        assertThat(LoceatParams.DEFAULT_LAT).isNotNull()
        assertThat(LoceatParams.DEFAULT_LAT).isEqualTo(DEFAULT_LAT.toDouble())
        assertThat(LoceatParams.DEFAULT_LNG).isNotNull()
        assertThat(LoceatParams.DEFAULT_LNG).isEqualTo(DEFAULT_LNG.toDouble())
        assertThat(LoceatParams.DEFAULT_ADDRESS).isNotNull()
        assertThat(LoceatParams.DEFAULT_ADDRESS).isEqualTo(DEFAULT_ADDRESS)
    }
}
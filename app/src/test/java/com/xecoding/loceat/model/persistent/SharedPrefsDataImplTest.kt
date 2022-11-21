package com.xecoding.loceat.model.persistent

import com.google.common.truth.Truth.assertThat
import com.xecoding.loceat.common.BaseSharedPrefsTest
import org.junit.Test

class SharedPrefsDataImplTest: BaseSharedPrefsTest() {

    @Test
    fun writeDefaultLat() {
        sharedPrefsData.write(Settings.DEFAULT_LAT,
            DEFAULT_LAT
        )
        val defaultLat = sharedPrefsData.read(Settings.DEFAULT_LAT,
            DEFAULT_LAT
        )
        assertThat(defaultLat).isEqualTo(DEFAULT_LAT)
    }

    @Test
    fun writeDefaultLng() {
        sharedPrefsData.write(Settings.DEFAULT_LNG,
            DEFAULT_LNG
        )
        val defaultLng = sharedPrefsData.read(Settings.DEFAULT_LNG,
            DEFAULT_LNG
        )
        assertThat(defaultLng).isEqualTo(DEFAULT_LNG)
    }

    @Test
    fun writeDefaultAddress() {
        sharedPrefsData.write(Settings.DEFAULT_ADDRESS,
            DEFAULT_ADDRESS
        )
        val defaultAddress = sharedPrefsData.read(Settings.DEFAULT_ADDRESS,
            DEFAULT_ADDRESS
        )
        assertThat(defaultAddress).isEqualTo(DEFAULT_ADDRESS)
    }

    @Test
    fun verifyWrongLat() {
        val lat = sharedPrefsData.read(Settings.DEFAULT_LAT, null as String?)
        assertThat(lat).isNull()
    }

    @Test
    fun verifyWrongLng() {
        val lng = sharedPrefsData.read(Settings.DEFAULT_LNG, null as String?)
        assertThat(lng).isNull()
    }

    @Test
    fun verifyWrongAddress() {
        val address = sharedPrefsData.read(Settings.DEFAULT_ADDRESS, null as String?)
        assertThat(address).isNull()
    }
}
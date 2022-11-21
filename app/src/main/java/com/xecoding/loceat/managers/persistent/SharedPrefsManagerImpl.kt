package com.xecoding.loceat.managers.persistent

import androidx.annotation.VisibleForTesting
import com.google.android.gms.maps.model.LatLng
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.model.persistent.Settings
import com.xecoding.loceat.model.persistent.SharedPrefsData
import com.xecoding.loceat.utils.LoceatParams

class SharedPrefsManagerImpl(private val settings: SharedPrefsData<Settings>) : SharedPrefsManager {
    override fun setupDefaultLocation() {
        settings.read(Settings.DEFAULT_LAT, "39.106817")?.toDouble()?.let {
            LoceatParams.DEFAULT_LAT = it
        }
        settings.read(Settings.DEFAULT_LNG, "26.557912")?.toDouble()?.let {
            LoceatParams.DEFAULT_LNG = it
        }
        settings.read(Settings.DEFAULT_ADDRESS, "Pl. Sapfous, Mitilini 811 00")?.let {
            LoceatParams.DEFAULT_ADDRESS = it
        }
    }

    override fun getDefaultLocation(): GeoCoderLocation {
        return GeoCoderLocation(
            LatLng(LoceatParams.DEFAULT_LAT, LoceatParams.DEFAULT_LNG),
            LoceatParams.DEFAULT_ADDRESS
        )
    }

    override fun storeLocation(geoCoderLocation: GeoCoderLocation) {
        val latLong = geoCoderLocation.latLng
        settings.write(Settings.DEFAULT_LAT, latLong.latitude.toString())
        settings.write(Settings.DEFAULT_LNG, latLong.longitude.toString())
        settings.write(Settings.DEFAULT_ADDRESS, geoCoderLocation.address)
    }

    @VisibleForTesting
    fun getLocation(): GeoCoderLocation {
        val geoCoderLocation = GeoCoderLocation()
        var lat = 0.0
        var lng = 0.0
        settings.read(Settings.DEFAULT_LAT, "39.106817")?.toDouble()?.let {
            lat = it
        }
        settings.read(Settings.DEFAULT_LNG, "26.557912")?.toDouble()?.let {
            lng = it
        }
        val latLng = LatLng(lat, lng)
        geoCoderLocation.latLng = latLng
        settings.read(Settings.DEFAULT_ADDRESS, "Pl. Sapfous, Mitilini 811 00")?.let {
            geoCoderLocation.address = it
        }

        return geoCoderLocation
    }
}
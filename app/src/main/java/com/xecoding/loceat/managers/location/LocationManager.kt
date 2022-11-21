package com.xecoding.loceat.managers.location

import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.GoogleMap
import com.xecoding.loceat.model.location.GeoCoderLocation

interface LocationManager {

    val geocoderLocation: LiveData<GeoCoderLocation>

    fun setMap(map: GoogleMap)

    fun setUpMarker(geoCoderLocation: GeoCoderLocation)

    fun requestGeolocation()

    fun requestSettings(
        showDialog: (exception: ResolvableApiException) -> Unit,
        showSettings: () -> Unit
    )
}
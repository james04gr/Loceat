package com.xecoding.loceat.model.location

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.xecoding.loceat.extension.stringConvert
import com.xecoding.loceat.utils.LoceatParams.DEFAULT_LAT
import com.xecoding.loceat.utils.LoceatParams.DEFAULT_LNG
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoCoderLocation(
    var latLng: LatLng = LatLng(DEFAULT_LAT, DEFAULT_LNG),
    var address: String = "",
    var isLoading: Boolean = true
): Parcelable

fun GeoCoderLocation.locString(): String {
    return this.latLng.stringConvert()
}
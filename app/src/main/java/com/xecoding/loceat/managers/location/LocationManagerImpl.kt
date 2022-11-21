package com.xecoding.loceat.managers.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.os.Looper
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.xecoding.loceat.extension.isDebug
import com.xecoding.loceat.extension.toast
import com.xecoding.loceat.managers.persistent.SharedPrefsManager
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.services.GeocodeService

class LocationManagerImpl(
    private val context: Context,
    private val sharedPrefsManager: SharedPrefsManager,
    private val locationRequest: LocationRequest,
    private val locationSettingsRequestBuilder: LocationSettingsRequest.Builder
) : LocationManager {

    private lateinit var googleMap: GoogleMap
    private val geoCoderLocation: GeoCoderLocation = GeoCoderLocation()
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val locationData: MutableLiveData<GeoCoderLocation> = MutableLiveData()

    override val geocoderLocation: LiveData<GeoCoderLocation> get() = locationData

    override fun setUpMarker(geoCoderLocation: GeoCoderLocation) {
        val latLng = geoCoderLocation.latLng
        val address = geoCoderLocation.address
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(latLng).title(address))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    @SuppressLint("MissingPermission")
    override fun requestGeolocation() {
        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    if (isLocationApproved(locationResult)) {
                        val location = locationResult.lastLocation ?: return
                        geoCoderLocation.latLng = LatLng(location.latitude, location.longitude)
                        removeLocationRequest(this)
                        isDebug { context.toast("Best Accuracy found ${location.accuracy}") }
                        GeocodeService.startGeocodeService(context, geoCoderLocation, locationData)
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun requestSettings(
        showDialog: (exception: ResolvableApiException) -> Unit,
        showSettings: () -> Unit
    ) {
        val locationSettingsResponse: Task<LocationSettingsResponse>
        locationSettingsRequestBuilder.also {
            locationSettingsResponse =
                LocationServices.getSettingsClient(context).checkLocationSettings(it.build())

            locationSettingsResponse.addOnCompleteListener { task ->
                try {
                    task.getResult(ApiException::class.java)
                    requestGeolocation()
                } catch (apiEx: ApiException) {
                    when (apiEx.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                showDialog(apiEx as ResolvableApiException)
                            } catch (e: IntentSender.SendIntentException) {
                                context.toast("${e.message}")
                                geoCoderLocation.isLoading = false
                                locationData.postValue(geoCoderLocation)
                            } catch (e: ClassCastException) {
                                context.toast("${e.message}")
                                geoCoderLocation.isLoading = false
                                locationData.postValue(geoCoderLocation)
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            showSettings.invoke()
                        }
                    }
                }
            }
        }
    }

    override fun setMap(map: GoogleMap) {
        this.googleMap = map
        setUpMarker(sharedPrefsManager.getDefaultLocation())
    }

    private fun removeLocationRequest(locationCallback: LocationCallback) {
        LocationServices.getFusedLocationProviderClient(context)
            .removeLocationUpdates(locationCallback)
    }

    private fun isLocationApproved(locationResult: LocationResult): Boolean =
        locationResult.lastLocation?.accuracy!! <= ACCURACY_REQUEST_THRESHOLD

    companion object {
        private const val ACCURACY_REQUEST_THRESHOLD = 25
    }
}
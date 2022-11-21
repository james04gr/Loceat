package com.xecoding.loceat.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.ResultReceiver
import androidx.lifecycle.MutableLiveData
import com.xecoding.loceat.BuildConfig
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.receivers.GeocodeReceiver
import java.util.*

class GeocodeService : IntentService(GeocodeService::class.simpleName) {

    private var resultReceiver: ResultReceiver? = null

    override fun onHandleIntent(i: Intent?) {
        i?.let { intent ->
            resultReceiver =
                intent.getParcelableExtra(BuildConfig.RESULT_ADDRESS_RECEIVER) ?: return
            resultReceiver?.let {
                val geoCoderLocation =
                    (intent.getParcelableExtra(BuildConfig.RESULT_ADDRESS_LOCATION) as GeoCoderLocation?) ?: return@let
                var address = ""
                val geocoder = Geocoder(this, Locale.getDefault())
                val latLng = geoCoderLocation.latLng
                try {
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                } catch (e: Exception) {
                    address = e.message.toString()
                    null
                }?.let {
                    address = it[0].getAddressLine(0)
                    sendResult(RESULT_ADDRESS_SUCCESS, address)
                } ?: run {
                    sendResult(RESULT_ADDRESS_FAILURE, address)
                }
            }
        }
    }

    private fun sendResult(resultCode: Int, address: String) {
        resultReceiver?.send(resultCode, Bundle().apply {
            putString(BuildConfig.RESULT_ADDRESS, address)
        })
    }

    companion object {
        private const val RESULT_ADDRESS_SUCCESS = 1989
        private const val RESULT_ADDRESS_FAILURE = 2020

        fun startGeocodeService(
            context: Context,
            geoCoderLocation: GeoCoderLocation,
            locationData: MutableLiveData<GeoCoderLocation>
        ) {
            Intent(context, GeocodeService::class.java).apply {
                putExtra(
                    BuildConfig.RESULT_ADDRESS_RECEIVER,
                    GeocodeReceiver(geoCoderLocation, locationData)
                )
                putExtra(BuildConfig.RESULT_ADDRESS_LOCATION, geoCoderLocation)
            }.also {
                context.startService(it)
            }
        }
    }
}
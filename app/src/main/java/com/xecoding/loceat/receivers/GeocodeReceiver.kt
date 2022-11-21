package com.xecoding.loceat.receivers

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.xecoding.loceat.BuildConfig
import com.xecoding.loceat.extension.isDebug
import com.xecoding.loceat.managers.location.LocationManager
import com.xecoding.loceat.model.location.GeoCoderLocation
import org.koin.core.KoinComponent
import org.koin.core.inject

class GeocodeReceiver(
    private val geoCoderLocation: GeoCoderLocation,
    private val locationEmitter: MutableLiveData<GeoCoderLocation>,
    handler: Handler = Handler()
) : ResultReceiver(handler), KoinComponent {

    private val locationManager: LocationManager by inject()

    companion object {
        private const val TAG = "GeocodeReceiver"
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)
        resultData?.let {
            val address = resultData.getString(BuildConfig.RESULT_ADDRESS) ?: "Unresolved address"
            isDebug { Log.d(TAG, address) }
            locationManager.setUpMarker(geoCoderLocation)
            geoCoderLocation.isLoading = false
            geoCoderLocation.address = address
            locationEmitter.postValue(geoCoderLocation)
        }
    }

}
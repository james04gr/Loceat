package com.xecoding.loceat.ui.activities.location

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.xecoding.loceat.R

import com.xecoding.loceat.extension.isAboveMarsh
import com.xecoding.loceat.extension.toast
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.ui.activities.venues.VenuesActivity
import com.xecoding.loceat.viewmodels.LocationViewModel
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mLocation: GeoCoderLocation
    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        lifecycle.addObserver(viewModel)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        requestLocation()

        continueBtn.setOnClickListener {
            viewModel.saveLocation(mLocation)
            VenuesActivity.startActivity(this, mLocation)
        }

        locationFab.setOnClickListener {
            requestLocation()
        }

        viewModel.location.observe(this, Observer {
            it?.let { data ->
                searchMode(data.isLoading)
                mLocation = data
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.setMap(googleMap)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                // Location Permission granted
                    showLocationSettingsPopup()
                else {
                    toast("Location permission denied")
                    searchMode(false)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOCATION_SETTINGS_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        // User enabled Location Settings
                        viewModel.applyLocationRequest()
                    }
                    RESULT_CANCELED -> {
                        // User chose not to enable Location Settings
                        searchMode(false)
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun requestLocation() {
        searchMode(true)
        isAboveMarsh(code = {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted
                showLocationSettingsPopup()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                    toast("Location permission needed to access your device location")
                // Runtime request location permission
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }, other = {
            // We don't need location permissions
            showLocationSettingsPopup()
        })
    }

    private fun showSettingsAlert() {
        AlertDialog.Builder(this).apply {
            setTitle("Allow loceat to access your location?")
            setMessage("We need access to your location to show you relevant search results")
            setPositiveButton("Allow") { _, _ ->
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    searchMode(false)
                    startActivity(this)
                }
            }
            setNegativeButton("Cancel") { dialog, _ ->
                searchMode(false)
                dialog.cancel()
            }
        }.also {
            it.show()
        }
    }

    private fun searchMode(isLoading: Boolean) {
        locationFab.isEnabled = !isLoading
        continueBtn.visibility = if (isLoading) View.GONE else View.VISIBLE
        continueBtn.isEnabled = !isLoading
        continueBtn.text = if (isLoading) "Finding your location" else "Continue"
    }

    private fun showLocationSettingsPopup() {
        viewModel.showLocationSettings(showDialog = {
            it.startResolutionForResult(
                this@LocationActivity,
                LOCATION_SETTINGS_REQUEST_CODE
            )
        }) {
            showSettingsAlert()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
        private const val LOCATION_SETTINGS_REQUEST_CODE = 200
    }
}
package com.xecoding.loceat.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.xecoding.loceat.extension.toCategoryGroups
import com.xecoding.loceat.model.entities.CategoryGroup
import com.xecoding.loceat.model.location.GeoCoderLocation
import com.xecoding.loceat.model.location.locString
import com.xecoding.loceat.model.response.Venues
import com.xecoding.loceat.repository.VenuesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.inject

class VenuesViewModel(private val savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val venuesRepository: VenuesRepository by inject()

    private val venuesData = MutableLiveData<List<CategoryGroup>>()
    val venues: LiveData<List<CategoryGroup>> = venuesData
    val address: String? = savedStateHandle.get<GeoCoderLocation>(EXTRA_LAT_LNG)?.address
    private lateinit var testVenuesRepository: VenuesRepository

    @VisibleForTesting
    constructor(testVenuesRepository: VenuesRepository) : this(SavedStateHandle()) {
        this.testVenuesRepository = testVenuesRepository
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun loadVenues() {
        viewModelScope.launch {
            savedStateHandle.getLiveData<GeoCoderLocation>(EXTRA_LAT_LNG).value?.let {
                val result = venuesRepository.fetchVenues(it.locString())
                withContext(Dispatchers.Main) {
                    venuesData.postValue(result.response.venues.toCategoryGroups())
                }
            }
        }
    }

    @VisibleForTesting
    fun loadDummyVenues(data: Venues) {
        venuesData.postValue(data.response.venues.toCategoryGroups())
    }

    @VisibleForTesting
    suspend fun getDummyVenues(geoCoderLocation: GeoCoderLocation): Venues {
        return testVenuesRepository.fetchVenues(geoCoderLocation.locString())
    }

    @VisibleForTesting
    fun getTestAddress(geoCoderLocation: GeoCoderLocation): String {
        return geoCoderLocation.address
    }

    companion object {
        const val EXTRA_LAT_LNG = "EXTRA_LAT_LNG"
    }
}
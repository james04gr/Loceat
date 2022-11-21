package com.xecoding.loceat.repository

import com.xecoding.loceat.model.response.Venues
import com.xecoding.loceat.network.LoceatApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VenuesRepositoryImpl(private val apiService: LoceatApiService): VenuesRepository {

    override suspend fun fetchVenues(location: String): Venues = withContext(Dispatchers.IO) {
        apiService.searchVenues(location)
    }
}
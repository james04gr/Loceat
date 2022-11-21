package com.xecoding.loceat.repository

import com.xecoding.loceat.model.response.Venues

interface VenuesRepository {

    suspend fun  fetchVenues(location: String): Venues
}
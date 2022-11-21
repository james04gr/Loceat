package com.xecoding.loceat.network

import com.xecoding.loceat.model.response.Venues
import retrofit2.http.GET
import retrofit2.http.Query

interface LoceatApiService {

    @GET("search")
    suspend fun searchVenues(@Query("ll") location: String): Venues
}
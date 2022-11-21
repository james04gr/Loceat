package com.xecoding.loceat.repository

import com.google.common.truth.Truth.assertThat
import com.xecoding.loceat.common.BaseWebServerUnitTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.koin.core.inject
import org.koin.core.qualifier.named

class VenuesRepositoryImplTest : BaseWebServerUnitTest() {
    private val venuesRepository by inject<VenuesRepository>()
    private val venuesResponse by inject<MockResponse>(named(VENUES_RESPONSE))

    @Test
    fun testGetVenues() {
        enqueue(venuesResponse)
        runBlocking {
            val venues = venuesRepository.fetchVenues("test")
            assertThat(venues.response.venues).isNotEmpty()
        }
    }
}
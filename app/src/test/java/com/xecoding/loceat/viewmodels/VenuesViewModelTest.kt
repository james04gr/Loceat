package com.xecoding.loceat.viewmodels

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.xecoding.loceat.common.BaseViewModelUnitTest
import com.xecoding.loceat.model.entities.CategoryGroup
import com.xecoding.loceat.model.location.locString
import com.xecoding.loceat.model.response.*
import com.xecoding.loceat.repository.VenuesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock

class VenuesViewModelTest : BaseViewModelUnitTest() {
    @Mock
    lateinit var venuesObserver: Observer<List<CategoryGroup>>

    @Mock
    lateinit var venuesRepository: VenuesRepository
    private lateinit var viewModel: VenuesViewModel

    override fun setUp() {
        super.setUp()
        viewModel = VenuesViewModel(venuesRepository)
    }

    @Test
    fun testAddress() {
        val address = viewModel.getTestAddress(geoCoderLocation)
        assertThat(address).isNotEmpty()
        assertThat(address).isEqualTo(DEFAULT_ADDRESS)
    }

    @Test
    fun testDummyVenuesData() {
        val data: MutableList<CategoryGroup> = arrayListOf()
        val venues: MutableList<Venue> = arrayListOf()
        val categories: MutableList<Category> = arrayListOf()
        val beenHere = BeenHere(1, 1, true, 1)
        categories.add(Category(Icon("a", "b"), "", "category1", "", false, ""))
        val location = Location(
            "", "", "",
            "",
            "",
            0,
            arrayListOf("a", "b"),
            arrayListOf(),
            DEFAULT_LAT.toDouble(),
            DEFAULT_LNG.toDouble(),
            "",
            "12345",
            ""
        )
        val venue = Venue(
            beenHere,
            categories,
            Contact(),
            false,
            HereNow(0, arrayListOf(Group(0, arrayListOf(), "", "")), "summary"),
            "",
            location,
            "",
            "",
            Stats(0, 0, 0, 0),
            arrayListOf(
                LabeledLatLng(
                    DEFAULT_ADDRESS,
                    DEFAULT_LAT.toDouble(),
                    DEFAULT_LNG.toDouble()
                )
            ),
            VenuePage(""),
            true
        )
        venues.add(venue)
        data.add(CategoryGroup("category1", venues))
        val meta = Meta(10, "ok")
        val response = Response(true, venues)
        val venuesData = Venues(meta, response)
        runBlocking {
            given(venuesRepository.fetchVenues(geoCoderLocation.locString())).willReturn(venuesData)
            val result = viewModel.getDummyVenues(geoCoderLocation)
            viewModel.loadDummyVenues(result)
        }
        viewModel.venues.observeForever(venuesObserver)
        verify(venuesObserver).onChanged(data)
    }
}
package com.xecoding.loceat.common

import org.junit.After
import org.junit.Before

open class BaseUnitTest {

    @Before
    open fun setUp() {
    }

    @After
    open fun tearDown() {
    }

    companion object {
        const val PREFS_NAME = "PREFS_NAME"
        const val DEFAULT_LAT = "39.106817"
        const val DEFAULT_LNG = "26.557912"
        const val DEFAULT_ADDRESS = "Pl. Sapfous, Mitilini 811 00"
    }
}
package com.xecoding.loceat.common

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.maps.model.LatLng
import com.xecoding.loceat.di.serviceModule
import com.xecoding.loceat.di.testLocationModule
import com.xecoding.loceat.di.testManagersModule
import com.xecoding.loceat.di.testNetworkModule
import com.xecoding.loceat.model.location.GeoCoderLocation
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@Ignore
open class BaseManagerTest : BaseLifecycleTest(), KoinTest {

    protected lateinit var context: Context
    protected val geoCoderLocation = GeoCoderLocation(
        LatLng(DEFAULT_LAT.toDouble(), DEFAULT_LNG.toDouble()),
        DEFAULT_ADDRESS,
        false
    )

    @Before
    override fun setUp() {
        super.setUp()
        context = ApplicationProvider.getApplicationContext<Context>()
        startKoin {
            androidContext(context)
            modules(
                arrayListOf(
                    testNetworkModule,
                    serviceModule,
                    testManagersModule,
                    testLocationModule
                )
            )
        }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        stopKoin()
    }
}
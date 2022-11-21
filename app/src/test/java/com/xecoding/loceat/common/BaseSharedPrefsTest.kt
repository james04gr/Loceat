package com.xecoding.loceat.common

import com.xecoding.loceat.managers.persistent.SharedPrefsManagerImpl
import com.xecoding.loceat.model.persistent.Settings
import com.xecoding.loceat.model.persistent.SharedPrefsDataImpl
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@Ignore
open class BaseSharedPrefsTest: BaseManagerTest() {

    protected lateinit var sharedPrefsData: SharedPrefsDataImpl<Settings>
    protected lateinit var sharedPrefsManager: SharedPrefsManagerImpl

    override fun setUp() {
        super.setUp()
        sharedPrefsData = SharedPrefsDataImpl(context,
            PREFS_NAME
        )
        sharedPrefsManager = SharedPrefsManagerImpl(sharedPrefsData)
    }
}
package com.xecoding.loceat.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.junit.Ignore
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@Ignore
open class BaseLifecycleTest: BaseUnitTest() {

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var lifecycle: Lifecycle

    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
    }
}
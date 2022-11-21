package com.xecoding.loceat.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Ignore
import org.junit.Rule

@Ignore
open class BaseViewModelUnitTest : BaseSharedPrefsTest() {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()
}
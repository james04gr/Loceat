package com.xecoding.loceat.common

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Ignore
import org.koin.core.inject

@Ignore
open class BaseWebServerUnitTest : BaseManagerTest() {
    private val mockWebServer by inject<MockWebServer>()
    override fun setUp() {
        super.setUp()
        mockWebServer.start()
    }

    override fun tearDown() {
        super.tearDown()
        mockWebServer.shutdown()
    }

    protected fun enqueue(response: MockResponse) {
        mockWebServer.enqueue(response)
    }

    protected fun takeRequest(): RecordedRequest {
        return mockWebServer.takeRequest()
    }

    companion object {
        internal const val VENUES_RESPONSE = "VENUES_RESPONSE"
    }
}
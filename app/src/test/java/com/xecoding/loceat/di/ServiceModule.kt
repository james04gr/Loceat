package com.xecoding.loceat.di

import com.xecoding.loceat.common.BaseWebServerUnitTest
import okhttp3.mockwebserver.MockResponse
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.net.HttpURLConnection

val serviceModule = module {
    single(named("VENUES")) {
        return@single FileUtil.readTestResourceFile("venues.json")
    }

    single(named(BaseWebServerUnitTest.VENUES_RESPONSE)) {
        return@single MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(get<String>(named("VENUES")))
    }
}

object FileUtil {
    fun readTestResourceFile(fileName: String): String {
        val fileInputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        return fileInputStream?.bufferedReader()?.readText() ?: ""
    }
}
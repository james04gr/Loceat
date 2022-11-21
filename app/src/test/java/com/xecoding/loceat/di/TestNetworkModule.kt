package com.xecoding.loceat.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.xecoding.loceat.network.LoceatApiService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val testNetworkModule = module {
    single<Gson> {
        val gsonBuilder = GsonBuilder()
        return@single gsonBuilder.create()
    }

    single {
        return@single OkHttpClient().newBuilder().build()
    }

    single<Retrofit> {
        return@single Retrofit.Builder()
            .baseUrl(get<MockWebServer>().url("/"))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        return@single MockWebServer()
    }

    single<LoceatApiService> {
        val retrofit = get<Retrofit>()
        return@single retrofit.create(LoceatApiService::class.java)
    }
}
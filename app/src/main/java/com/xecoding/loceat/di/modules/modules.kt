package com.xecoding.loceat.di.modules

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.xecoding.loceat.BuildConfig
import com.xecoding.loceat.managers.location.LocationManager
import com.xecoding.loceat.managers.location.LocationManagerImpl
import com.xecoding.loceat.managers.persistent.SharedPrefsManager
import com.xecoding.loceat.managers.persistent.SharedPrefsManagerImpl
import com.xecoding.loceat.model.persistent.Settings
import com.xecoding.loceat.model.persistent.SharedPrefsData
import com.xecoding.loceat.model.persistent.SharedPrefsDataImpl
import com.xecoding.loceat.network.BodyInterceptor
import com.xecoding.loceat.network.HeaderInterceptor
import com.xecoding.loceat.network.LoceatApiService
import com.xecoding.loceat.repository.VenuesRepository
import com.xecoding.loceat.repository.VenuesRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val managersModule = module {
    single<VenuesRepository> {
        return@single VenuesRepositoryImpl(get())
    }

    single<SharedPrefsData<Settings>> {
        return@single SharedPrefsDataImpl<Settings>(androidContext(), Settings.prefsName)
    }

    single<SharedPrefsManager> {
        return@single SharedPrefsManagerImpl(get())
    }

    single<LocationManager> {
        return@single LocationManagerImpl(androidContext(), get(), get(), get())
    }
}

val locationModule = module {
    single {
        return@single LocationRequest().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    single {
        return@single LocationSettingsRequest.Builder().apply {
            addLocationRequest(LocationRequest.create().apply {
                this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            })
        }
    }
}

val networkModule = module {
    single<Gson> {
        val gsonBuilder = GsonBuilder()
        return@single gsonBuilder.setLenient().create()
    }

    single {
        val loggingInterceptor = get<Interceptor?>()
        val httpClientBuilder = OkHttpClient().newBuilder()
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        if (loggingInterceptor != null) httpClientBuilder.addInterceptor(loggingInterceptor)
        httpClientBuilder.addInterceptor(get<HeaderInterceptor>())
        httpClientBuilder.addInterceptor(get<BodyInterceptor>())
        return@single httpClientBuilder.build()
    }

    single<Interceptor?> {
        if (!BuildConfig.DEBUG) return@single null
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return@single httpLoggingInterceptor
    }

    single {
        return@single HeaderInterceptor()
    }

    single {
        return@single BodyInterceptor()
    }

    single<Retrofit> {
        return@single Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_HOLDER_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>())
            .build()
    }

    single<LoceatApiService> {
        return@single get<Retrofit>().create(LoceatApiService::class.java)
    }
}
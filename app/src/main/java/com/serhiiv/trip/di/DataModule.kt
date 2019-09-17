package com.serhiiv.trip.di

import com.serhiiv.trip.BuildConfig
import com.serhiiv.trip.data.remote.GoogleDirectionsApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
class DataModule {

    @Provides
    fun provideGoogleDestinationsApiService(retrofit: Retrofit): GoogleDirectionsApiService =
        retrofit.create()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(GoogleDirectionsApiService.MAPS_ENDPOINT)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
    }
}

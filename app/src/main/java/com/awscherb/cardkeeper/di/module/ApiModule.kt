package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.BuildConfig
import com.awscherb.cardkeeper.data.api.PkPassApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun provideLevel() =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS
        else HttpLoggingInterceptor.Level.NONE

    @Provides
    fun provideOkHttp(level: HttpLoggingInterceptor.Level) = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(level))
        .build()

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun providePassApi(retrofit: Retrofit) = retrofit.create(PkPassApi::class.java)

}
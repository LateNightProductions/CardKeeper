package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.data.api.PkPassApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttp() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

    @Provides
    @Singleton
    fun providePassApi(retrofit: Retrofit) = retrofit.create(PkPassApi::class.java)

}
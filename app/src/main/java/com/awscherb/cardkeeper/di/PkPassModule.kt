package com.awscherb.cardkeeper.di

import android.content.Context
import androidx.room.Room
import com.awscherb.cardkeeper.pkpass.api.PkPassApi
import com.awscherb.cardkeeper.pkpass.db.PkPassDatabase
import com.awscherb.cardkeeper.pkpass.handler.PkPassHandler
import com.awscherb.cardkeeper.pkpass.service.PkPassService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PkPassModule {

    companion object {

        @Provides
        fun provideLevel(buildInfo: com.awscherb.cardkeeper.common.BuildInfo) =
            if (buildInfo.debug) HttpLoggingInterceptor.Level.HEADERS
            else HttpLoggingInterceptor.Level.NONE

        @Provides
        fun provideOkHttp(level: HttpLoggingInterceptor.Level) = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(level))
            .build()

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

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): PkPassDatabase {
            return Room.databaseBuilder(
                context.applicationContext, PkPassDatabase::class.java, "pkpass.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun providePkPassDao(db: PkPassDatabase) = db.pkPassDao()
    }

    @Binds
    fun bindPkPassService(handler: PkPassHandler): PkPassService

}
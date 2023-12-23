package com.awscherb.cardkeeper.di

import com.awscherb.cardkeeper.pkpass.util.PassDateUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Type
import java.util.Date

@Module
@InstallIn(SingletonComponent::class)
interface GsonModule {

    companion object {
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                    override fun deserialize(
                        json: JsonElement?,
                        typeOfT: Type?,
                        context: JsonDeserializationContext?
                    ): Date? {
                        val s = json?.asJsonPrimitive?.asString ?: return null

                        try {
                            PassDateUtils.networkFormat.parse(s)
                        } catch (e: Exception) {
                            try {
                                PassDateUtils.timezoneFormat.parse(s)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        return null
                    }

                })
                .create()
        }
    }
}
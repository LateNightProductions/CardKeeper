package com.awscherb.cardkeeper.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PkPassApi {

    @GET
    suspend fun getPass(@Url webServiceUrl: String): Response<ResponseBody>
}
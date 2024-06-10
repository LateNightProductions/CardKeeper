package com.awscherb.cardkeeper.pkpass.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface PkPassApi {

    @GET
    suspend fun getPass(
        @Url webServiceUrl: String, @Header("Authorization") auth: String
    ): Response<ResponseBody>
}
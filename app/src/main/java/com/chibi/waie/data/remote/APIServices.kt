package com.chibi.waie.data.remote

import com.chibi.waie.model.Astronomy
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface APIServices {

    @GET
    suspend fun getAstronomyDetails(
        @Url url: String?,
        @Query("api_key") api_key : String?
    ): Response<Astronomy>?
}
package com.example.eatup.Interface


import com.example.eatup.model.WebData
import com.example.eatup.model.WebDataItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("{rawValue}")
    suspend fun getRawValue(
        @Path("rawValue") rawValue: String
    ) : Response<List<WebDataItem>>
}
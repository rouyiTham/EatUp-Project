package com.example.eatup.repo

import com.example.eatup.RetrofitInstance
import com.example.eatup.model.WebData
import com.example.eatup.model.WebDataItem
import retrofit2.Response

class Repository {
    suspend fun getRawValue(rawValue: String): Response<List<WebDataItem>> {
        return RetrofitInstance.api.getRawValue(rawValue)
    }
}
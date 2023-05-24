package com.example.eatup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eatup.model.WebData
import com.example.eatup.model.WebDataItem
import com.example.eatup.repo.Repository
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel(private val repository: Repository): androidx.lifecycle.ViewModel(){

    val myResponse : MutableLiveData<Response<List<WebDataItem>>> = MutableLiveData()

    fun getRawValue(rawValue: String){
        viewModelScope.launch {
            val response = repository.getRawValue(rawValue)
            myResponse.value = response
        }
    }
}
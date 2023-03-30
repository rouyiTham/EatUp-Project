package com.example.eatup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eatup.model.RetrievalData
import com.example.eatup.repo.NGOrepo

class NGOviewmodel: ViewModel() {
    private val repository : NGOrepo
    private val _alldata = MutableLiveData<List<RetrievalData>>()
    val alldata: LiveData<List<RetrievalData>> = _alldata

    init {

        repository = NGOrepo().getInstance()
        repository.loadUsers(_alldata)

    }

}

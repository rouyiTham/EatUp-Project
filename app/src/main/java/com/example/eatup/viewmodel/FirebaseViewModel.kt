package com.example.eatup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eatup.adapter.FoodAdapter
import com.example.eatup.model.FoodModel
import com.example.eatup.model.ParentModel
import com.example.eatup.repo.firebaserepo
import com.example.eatup.repo.firebaserepo.OnRealtimeDbTaskComplete
import com.google.firebase.database.DatabaseError

 class FirebaseViewModel : ViewModel(), firebaserepo.OnRealtimeDbTaskComplete {
    val parentModelMutableLiveData = MutableLiveData<List<ParentModel>?>()
    val databaseErrorMutableLiveData = MutableLiveData<DatabaseError?>()
    private val firebaserepo: firebaserepo = firebaserepo(this)

    val allData: Unit
        get() {
            firebaserepo.allData
        }

    override fun onSuccess(parentModelList: List<ParentModel>){
        parentModelMutableLiveData.value = parentModelList
    }


     override fun onFailure(error: DatabaseError) {
        databaseErrorMutableLiveData.value = error
    }

}
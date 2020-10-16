package com.simplx.apps.ysolutiontask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.simplx.apps.ysolutiontask.pojo.Food

class FoodViewModel : ViewModel() {


    fun getFoods(): LiveData<ArrayList<Food>> {
        return FoodRepository.getFoods()
    }

    fun cancelJob() {
        FoodRepository.cancelJob()
    }
}
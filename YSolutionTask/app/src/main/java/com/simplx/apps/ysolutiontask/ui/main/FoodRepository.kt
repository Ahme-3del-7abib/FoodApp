package com.simplx.apps.ysolutiontask.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import com.simplx.apps.ysolutiontask.api.RetrofitBuilder
import com.simplx.apps.ysolutiontask.pojo.Food
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object FoodRepository {

    private const val TAG = "FoodRepository"
    var job: CompletableJob? = null

    fun getFoods(): LiveData<ArrayList<Food>> {

        job = Job()

        return object : LiveData<ArrayList<Food>>() {
            override fun onActive() {
                super.onActive()

                job?.let {
                    CoroutineScope(IO + it).launch {

                        val result = RetrofitBuilder.apiService.getFoods()

                        if (result.isSuccessful) {
                            withContext(Main) {
                                value = result.body()
                                it.complete()
                            }
                        } else {
                            Log.d(TAG, "There is an error, try again later .. ")
                        }
                    }
                }
            }
        }
    }

    fun cancelJob() {
        job?.cancel()
    }

}
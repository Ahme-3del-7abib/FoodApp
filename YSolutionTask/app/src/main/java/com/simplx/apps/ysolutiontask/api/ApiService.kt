package com.simplx.apps.ysolutiontask.api

import com.simplx.apps.ysolutiontask.pojo.Food
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/android-test/recipes.json")
    suspend fun getFoods(): Response<ArrayList<Food>>

}
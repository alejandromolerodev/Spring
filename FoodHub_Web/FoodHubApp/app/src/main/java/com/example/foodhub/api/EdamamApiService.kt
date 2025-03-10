package com.example.foodhub.api

import com.example.foodhub.modelo.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamApiService {
    @GET("recipes/v2")
    fun getRecipes(
        @Query("type") type: String = "public",
        @Query("q") query: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("diet") diet: List<String>?,
        @Query("health") health: List<String>?
    ): Call<RecipeResponse>
}
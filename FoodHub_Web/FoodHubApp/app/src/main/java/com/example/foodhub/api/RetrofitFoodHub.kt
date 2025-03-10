package com.example.foodhub.api

import com.example.foodhub.modelo.NutriScore
import com.example.foodhub.modelo.NutriScoreDeserializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFoodHub {
    private const val BASE_URL = "http://192.168.21.241:8080/"

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .registerTypeAdapter(NutriScore::class.java, NutriScoreDeserializer())
        .create()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: FoodHubApiService by lazy {
        retrofit.create(FoodHubApiService::class.java)
    }
}
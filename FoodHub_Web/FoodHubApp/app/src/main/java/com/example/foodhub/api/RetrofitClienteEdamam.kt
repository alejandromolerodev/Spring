package com.example.foodhub.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClienteEdamam {
    private const val BASE_URL = "https://api.edamam.com/api/"
    private val userID = "TU_USER_ID"  // Reemplaza con tu userID real

    // Interceptor para agregar headers
    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Edamam-Account-User", userID)
            .build()
        chain.proceed(request)
    }

    // Configurar OkHttpClient con el Interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val instance: EdamamApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EdamamApiService::class.java)
    }
}
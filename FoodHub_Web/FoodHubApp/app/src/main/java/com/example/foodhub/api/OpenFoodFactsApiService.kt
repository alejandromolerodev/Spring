package com.example.foodhub.api

import com.example.foodhub.modelo.ProductoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface OpenFoodFactsApiService {
    @GET("api/v0/product/{barcode}.json")
    suspend fun getProductByBarcode(@Path("barcode") barcode: String): Response<ProductoResponse>
}

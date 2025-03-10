package com.example.foodhub.api

import Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FoodHubApiService {
    @GET("foodhub")
    suspend fun obtenerTodosProductos(): Response<List<Producto>>

    @GET("foodhub/{id}")
    suspend fun obtenerProductoPorId(
        @Path("id") id: Long
    ): Response<Producto>

    // Endpoint actualizado para coincidir con la API
    @GET("foodhub/{id}/imagen")
    suspend fun obtenerImagenProducto(
        @Path("id") id: Long
    ): Response<String>

    @POST("foodhub")
    suspend fun crearProducto(
        @Body producto: Producto
    ): Response<Producto>

    @PUT("foodhub/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: Long,
        @Body producto: Producto
    ): Response<Producto>

    @DELETE("foodhub/{id}")
    suspend fun eliminarProducto(
        @Path("id") id: Long
    ): Response<Unit>


}
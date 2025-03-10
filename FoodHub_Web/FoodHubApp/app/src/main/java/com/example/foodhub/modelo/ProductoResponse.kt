package com.example.foodhub.modelo

import Producto
import com.google.gson.annotations.SerializedName

// Clase de datos que representa la respuesta JSON de la API.
// Se usa la anotación @SerializedName para mapear el campo "product" del JSON al atributo "producto".
data class ProductoResponse(
    @SerializedName("product") val producto: Producto
)

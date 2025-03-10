package com.example.foodhub.modelo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categoria(
    @SerializedName("nombre")
    val nombre: String = "Sin categoría"
) : Parcelable {
    companion object {
        val EMPTY = Categoria()
    }
}
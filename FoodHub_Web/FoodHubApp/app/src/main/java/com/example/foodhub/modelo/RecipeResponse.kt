package com.example.foodhub.modelo

data class RecipeResponse(
    val hits: List<RecipeHit>
)

data class RecipeHit(
    val recipe: Recipe
)

data class Recipe(
    val label: String,
    val image: String,
    val url: String,
    val ingredientLines: List<String>,
    val calories: Double,
    val yield: Int, // Número de porciones
    val cautions: List<String>, // Advertencias
    val totalTime: Int, // Tiempo total en minutos
    val totalNutrients: Map<String, Nutrient>, // Nutrientes totales
    var esFavorito: Boolean = false

)
data class Nutrient(
    val label: String,
    val quantity: Double,
    val unit: String
)


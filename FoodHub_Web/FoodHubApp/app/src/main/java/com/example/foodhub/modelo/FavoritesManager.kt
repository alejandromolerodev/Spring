package com.example.foodhub.modelo

object FavoritesManager {
    private val favorites = mutableListOf<Recipe>()

    fun addFavorite(recipe: Recipe) {
        if (!favorites.any { it.label == recipe.label }) {
            favorites.add(recipe)
        }
    }

    fun removeFavorite(recipe: Recipe) {
        favorites.removeAll { it.label == recipe.label }
    }

    fun getFavorites(): List<Recipe> = favorites.toList()
}
package com.example.foodhub.modelo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodhub.R
import android.widget.Toast

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewRecipe)
        val textView: TextView = view.findViewById(R.id.textViewRecipeName)
        val ingredientsTextView: TextView = view.findViewById(R.id.textViewIngredients)
        val tvDes: TextView = view.findViewById(R.id.textViewDes)
        val yieldTextView: TextView = view.findViewById(R.id.textViewYield)
        val cautionsTextView: TextView = view.findViewById(R.id.textViewCautions)
        val totalTimeTextView: TextView = view.findViewById(R.id.textViewTotalTime)
        val nutrientsTextView: TextView = view.findViewById(R.id.textViewNutrients)
        val botonFavorito: ImageView = view.findViewById(R.id.botonFavorito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.textView.text = recipe.label

        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.imageView)

        val ingredientsInfo = recipe.ingredientLines.joinToString("\n")
        holder.ingredientsTextView.text = "Ingredientes:\n$ingredientsInfo"

        val calories = recipe.calories.toInt()
        holder.tvDes.text = "Calorías: $calories"

        holder.yieldTextView.text = "Raciones: ${recipe.yield}"
        holder.cautionsTextView.text = "Advertencias: " + recipe.cautions.joinToString(", ")
        holder.totalTimeTextView.text = "Tiempo preparación: ${recipe.totalTime} minutos"

        val nutrientsInfo = recipe.totalNutrients.map {
            val quantity = it.value.quantity.toInt()
            "${it.value.label}: $quantity ${it.value.unit}"
        }.joinToString("\n")
        holder.nutrientsTextView.text = "Nutrientes:\n$nutrientsInfo"

        holder.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.url))
            holder.itemView.context.startActivity(intent)
        }

        val esFavorito = FavoritesManager.getFavorites().any { it.label == recipe.label }
        updateFavoriteIcon(holder.botonFavorito, esFavorito)

        holder.botonFavorito.setOnClickListener {
            val isCurrentlyFavorite = FavoritesManager.getFavorites().any { it.label == recipe.label }

            if (isCurrentlyFavorite) {
                FavoritesManager.removeFavorite(recipe)
                Toast.makeText(holder.itemView.context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
            } else {
                FavoritesManager.addFavorite(recipe)
                Toast.makeText(holder.itemView.context, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            }
            updateFavoriteIcon(holder.botonFavorito, !isCurrentlyFavorite)
        }
    }

    private fun updateFavoriteIcon(boton: ImageView, isFavorite: Boolean) {
        boton.setImageResource(
            if (isFavorite) R.drawable.favorito_marcado else R.drawable.favorito_desmarcado
        )
    }

    override fun getItemCount() = recipes.size
}
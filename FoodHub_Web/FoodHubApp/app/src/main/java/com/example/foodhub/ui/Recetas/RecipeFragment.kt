package com.example.foodhub.ui.Recetas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodhub.R
import com.example.foodhub.api.RetrofitClienteEdamam
import com.example.foodhub.modelo.FavoritesManager
import com.example.foodhub.modelo.RecipeAdapter
import com.example.foodhub.modelo.RecipeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeFragment : Fragment() {

    private val APP_ID = "bb00fc2b"
    private val APP_KEY = "9aa39ac3a3b3dce15ac9695852f9461f"
    private lateinit var etQuery: EditText
    private lateinit var btBuscar: Button
    private lateinit var btFiltro: Button
    private lateinit var recyclerView: RecyclerView

    private var selectedFilter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recetas, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewRecipes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        etQuery = view.findViewById(R.id.editTextQuery)
        btBuscar = view.findViewById(R.id.botonBuscar)
        btFiltro = view.findViewById(R.id.botonFiltro)

        btBuscar.setOnClickListener {
            buscarRecetas()
        }
        btFiltro.setOnClickListener {
            mostrarDialogoFiltros()
        }

        return view
    }

    private fun buscarRecetas(
        selectedDietFilters: List<String> = emptyList(),
        selectedHealthFilters: List<String> = emptyList(),
        esFavorito: Boolean = false
    ) {
        val queryInput = etQuery.text.toString()
        if (queryInput.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor, ingresa un ingrediente",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (esFavorito) {
            val favorites = FavoritesManager.getFavorites()
            var filteredFavorites = favorites.filter {
                it.label.contains(queryInput, ignoreCase = true)
            }
            if (selectedHealthFilters.contains("gluten-free")) {
                filteredFavorites = filteredFavorites.filter { !it.cautions.contains("Gluten") }
            }
            if (selectedHealthFilters.contains("dairy-free")) {
                filteredFavorites = filteredFavorites.filter { !it.cautions.contains("Milk") }
            }
            if (filteredFavorites.isEmpty()) {
                Toast.makeText(requireContext(), "No hay favoritos con estos criterios", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = null
            } else {
                recyclerView.adapter = RecipeAdapter(requireContext(), filteredFavorites)
            }
            return
        }

        val queryForApi = queryInput.replace(" ", "+")
        val dietParams: List<String>? =
            if (selectedDietFilters.isNotEmpty()) selectedDietFilters else null
        val healthParams: List<String>? =
            if (selectedHealthFilters.isNotEmpty()) selectedHealthFilters else null

        val call = RetrofitClienteEdamam.instance.getRecipes(
            type = "public",
            query = queryForApi,
            appId = APP_ID,
            appKey = APP_KEY,
            diet = dietParams?.ifEmpty { null },
            health = healthParams?.ifEmpty { null }
        )

        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    var recipes = response.body()?.hits?.map { it.recipe } ?: emptyList()
                    if (selectedHealthFilters.contains("gluten-free")) {
                        recipes = recipes.filter { !it.cautions.contains("Gluten") }
                    }
                    if (selectedHealthFilters.contains("dairy-free")) {
                        recipes = recipes.filter { !it.cautions.contains("Milk") }
                    }
                    if (recipes.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "No se encontraron recetas con ese/los ingrediente(s)",
                            Toast.LENGTH_SHORT
                        ).show()
                        recyclerView.adapter = null
                    } else {
                        recyclerView.adapter = RecipeAdapter(requireContext(), recipes)
                    }
                } else {
                    Toast.makeText(requireContext(), "Error en la respuesta", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogoFiltros() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_filters, null)
        val containerFiltros = (dialogView as ScrollView).getChildAt(0) as LinearLayout
        val layoutDieteticos = dialogView.findViewById<LinearLayout>(R.id.layoutDieteticos)
        val layoutSalud = dialogView.findViewById<LinearLayout>(R.id.layoutSalud)

        val dietOptions = listOf(
            "Baja en grasas" to "low-fat",
            "Alta en proteínas" to "high-protein",
            "Bajo en carbohidratos" to "low-carb",
            "Alto en fibra" to "high-fiber",
            "Bajo en sodio" to "low-sodium"
        )
        val healthOptions = listOf(
            "Vegana" to "vegan",
            "Sin gluten" to "gluten-free",
            "Bajo en azúcares" to "low-sugar",
            "Vegetariana" to "vegetarian",
            "Sin lactosa" to "dairy-free"
        )

        val dietCheckboxes = mutableListOf<CheckBox>()
        for ((label, _) in dietOptions) {
            val checkBox = CheckBox(requireContext())
            checkBox.text = label
            layoutDieteticos.addView(checkBox)
            dietCheckboxes.add(checkBox)
        }

        val healthCheckboxes = mutableListOf<CheckBox>()
        for ((label, _) in healthOptions) {
            val checkBox = CheckBox(requireContext())
            checkBox.text = label
            layoutSalud.addView(checkBox)
            healthCheckboxes.add(checkBox)
        }

        val favoritesCheckbox = CheckBox(requireContext()).apply {
            text = "Mostrar solo favoritos"
        }
        containerFiltros.addView(favoritesCheckbox)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Selecciona filtros")
        builder.setView(dialogView)
        builder.setPositiveButton("Aplicar") { dialog, _ ->
            val selectedDietFilters = mutableListOf<String>()
            val selectedHealthFilters = mutableListOf<String>()

            for (i in dietCheckboxes.indices) {
                if (dietCheckboxes[i].isChecked) {
                    selectedDietFilters.add(dietOptions[i].second)
                }
            }
            for (i in healthCheckboxes.indices) {
                if (healthCheckboxes[i].isChecked) {
                    selectedHealthFilters.add(healthOptions[i].second)
                }
            }

            val mostrarFavoritos = favoritesCheckbox.isChecked

            buscarRecetas(selectedDietFilters, selectedHealthFilters, mostrarFavoritos)
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
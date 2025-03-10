package com.example.foodhub.modelo

import Producto
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.api.RetrofitFoodHub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ProductoViewModel : ViewModel() {

    private val _producto = MutableLiveData<Producto?>()
    private val _productos = MutableLiveData<List<Producto>>()
    private val _error = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()

    val producto: LiveData<Producto?> get() = _producto
    val productos: LiveData<List<Producto>> get() = _productos
    val error: LiveData<String> get() = _error
    val loading: LiveData<Boolean> get() = _loading

    init {
        cargarProductosDeAPI()
    }

    fun cargarProductosDeAPI() {
        _loading.postValue(true) // Muestra el loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitFoodHub.apiService.obtenerTodosProductos() // Llamada a la API para obtener todos los productos
                if (response.isSuccessful) {
                    val sortedProducts = response.body()?.sortedBy { it.nombre } ?: emptyList()
                    _productos.postValue(sortedProducts) // Actualiza la lista de productos
                } else {
                    _error.postValue("Error al cargar: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun fetchProductByBarcode(barcode: String) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://world.openfoodfacts.org/api/v0/product/$barcode.json")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        response.body?.string()?.let { jsonString ->
                            val jsonObject = JSONObject(jsonString)
                            if (jsonObject.getInt("status") == 1) {
                                val productJson = jsonObject.getJSONObject("product")
                                val product = parseOpenFoodFactsData(productJson, barcode)
                                _producto.postValue(product) // Actualiza el LiveData 'producto'
                            } else {
                                _error.postValue("Producto no encontrado")
                            }
                        }
                    } else {
                        _error.postValue("Error de API: ${response.code}")
                    }
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message ?: "Error desconocido"}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun guardarProductoEnAPI(producto: Producto) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val formattedDate = producto.fechaCad?.let {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                }
                val productToSave = Producto(
                    nombre = producto.nombre,
                    marca = producto.marca,
                    nutriScore = producto.nutriScore ?: NutriScore.UNKNOWN,
                    peso = producto.peso,
                    categoria = producto.categoria,
                    imagenUrl = producto.imagenUrl,
                    codigoBarras = producto.codigoBarras,
                    fechaCad = formattedDate?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it) }
                )

                val response = RetrofitFoodHub.apiService.crearProducto(productToSave)
                if (response.isSuccessful) {
                    cargarProductosDeAPI()
                } else {
                    _error.postValue("Error al guardar: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun parseOpenFoodFactsData(productJson: JSONObject, barcode: String): Producto {
        return Producto(
            nombre = productJson.optString("product_name", "Producto Desconocido"),
            marca = productJson.optString("brands").split(",").firstOrNull()?.trim() ?: "Marca no disponible",
            nutriScore = NutriScore.fromString(productJson.optString("nutriscore_grade", "UNKNOWN")),
            peso = productJson.optString("quantity", "Peso no especificado"),
            categoria = parseCategories(productJson.optString("categories")),
            imagenUrl = productJson.optString("image_url", null),
            codigoBarras = barcode,
            fechaCad = parseDate(productJson.optString("expiration_date"))
        )
    }

    private fun parseCategories(categories: String): Categoria? {
        return categories.split(',').firstOrNull()?.trim()?.takeIf { it.isNotBlank() }
            ?.let { Categoria(nombre = it) }
    }

    private fun parseDate(dateString: String): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }


    // En ProductoViewModel.kt - Mejorar el método de eliminación
    fun eliminarProductoEnAPI(id: Long) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitFoodHub.apiService.eliminarProducto(id)
                if (response.isSuccessful) {
                    Log.d("DELETE", "Eliminación exitosa. Recargando datos...")
                    cargarProductosDeAPI() // Recargar datos actualizados
                } else {
                    _error.postValue("Error al eliminar: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de red: ${e.message}")
                Log.e("DELETE", "Excepción: ${e.stackTraceToString()}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun resetProductState() {
        _producto.value = null
    }
}

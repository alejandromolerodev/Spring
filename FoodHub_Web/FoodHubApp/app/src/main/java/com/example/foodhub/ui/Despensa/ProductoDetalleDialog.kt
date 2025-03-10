package com.example.foodhub.ui.Despensa

import Producto
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodhub.R
import com.example.foodhub.modelo.ProductoViewModel
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Locale

class ProductoDetalleDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar la vista del diálogo
        val view = inflater.inflate(R.layout.dialog_producto_detalle, container, false)

        // Configurar la barra de herramientas (Toolbar)
        setupToolbar(view)

        // Cargar los datos del producto
        loadProductData(view)

        return view
    }

    private fun setupToolbar(view: View) {
        view.findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener { dismiss() }
    }

    private fun loadProductData(view: View) {
        arguments?.getParcelable<Producto>("producto")?.let { producto ->
            with(view) {
                // Configurar la información básica del producto
                setupBasicInfo(producto)

                // Configurar la información adicional del producto
                setupAdditionalInfo(producto)

                // Configurar el botón de eliminar
                val deleteButton = findViewById<Button>(R.id.deleteButton)
                deleteButton.setOnClickListener {
                    // Obtener el ViewModel desde el Fragmento padre (usando requireActivity en lugar de requireParentFragment)
                    val viewModel = ViewModelProvider(requireActivity()).get(ProductoViewModel::class.java)

                    // Llamar al método de eliminación directamente con el ID del producto
                    viewModel.eliminarProductoEnAPI(producto.id!!)

                    // Después de eliminar el producto, cerrar el diálogo
                    dismiss()
                    viewModel.cargarProductosDeAPI()

                }
            }
        }
    }

    private fun View.setupBasicInfo(producto: Producto) {
        findViewById<MaterialToolbar>(R.id.toolbar).title = producto.nombre

        Glide.with(context)
            .load(producto.imagenUrl)
            .placeholder(R.drawable.placeholder_comida)
            .error(R.drawable.error_imagen)
            .into(findViewById(R.id.imgProducto))

        findViewById<TextView>(R.id.txtCodigoBarras).text = "Código: ${producto.codigoBarras}"

        Glide.with(context)
            .load("https://barcode.tec-it.com/barcode.ashx?data=${producto.codigoBarras}")
            .placeholder(R.drawable.placeholder_barcode)
            .error(R.drawable.error_imagen)
            .into(findViewById(R.id.imgBarcode))
    }

    private fun View.setupAdditionalInfo(producto: Producto) {
        // Actualizar referencias a los campos modificados
        findViewById<TextView>(R.id.txtMarcaNuevo).text = "Marca: ${producto.marca}"
        findViewById<TextView>(R.id.txtCategorias).text = producto.categoria?.nombre ?: "Sin categoría"
        findViewById<TextView>(R.id.txtPesoNuevo).text = "Peso: ${producto.peso}"

        val fechaFormateada = producto.fechaCad?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
        } ?: "No especificada"
        findViewById<TextView>(R.id.txtFechaNuevo).text = "Caduca: $fechaFormateada"

        val nutriScoreResource = when(producto.nutriScore?.toString()?.uppercase()) {
            "A" -> R.drawable.nutriscorea
            "B" -> R.drawable.nutriscoreb
            "C" -> R.drawable.nutriscorec
            "D" -> R.drawable.nutriscored
            "E" -> R.drawable.nutriscoree
            else -> R.drawable.nutriscoregris
        }
        findViewById<ImageView>(R.id.imgNutriScore).setImageResource(nutriScoreResource)
    }

    companion object {
        fun newInstance(producto: Producto) = ProductoDetalleDialog().apply {
            arguments = Bundle().apply { putParcelable("producto", producto) }
        }
    }
}

package com.example.foodhub.ui.Despensa

import Producto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodhub.R
import com.example.foodhub.databinding.ItemProductoBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ExpandableProductAdapter(
    private val products: MutableList<Producto>
) : RecyclerView.Adapter<ExpandableProductAdapter.ViewHolder>() {

    var onItemClick: ((Producto) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: Producto) {
            with(binding) {
                // Configurar vistas
                productName.text = producto.nombre
                // Actualizar la referencia a categoria.nombre

                // Manejo seguro de URLs de imagen
                Glide.with(root.context)
                    .load(producto.imagenUrl?.takeIf { it.isNotBlank() } ?: R.drawable.placeholder_comida)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.error_imagen)
                    .into(productImage)

                // Fecha de caducidad
                producto.fechaCad?.let {
                    productExpiry.text = "Caduca: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)}"
                    productExpiry.visibility = View.VISIBLE
                } ?: run {
                    productExpiry.visibility = View.GONE
                }

                // Click listener
                root.setOnClickListener {
                    onItemClick?.invoke(producto)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    fun updateProducts(newProducts: List<Producto>) {
        val diffResult = DiffUtil.calculateDiff(ProductoDiffCallback(products, newProducts))
        products.clear()
        products.addAll(newProducts)
        diffResult.dispatchUpdatesTo(this)
    }

    private class ProductoDiffCallback(
        private val oldList: List<Producto>,
        private val newList: List<Producto>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldPos: Int, newPos: Int) =
            oldList[oldPos].codigoBarras == newList[newPos].codigoBarras

        override fun areContentsTheSame(oldPos: Int, newPos: Int) =
            oldList[oldPos] == newList[newPos]
    }
}
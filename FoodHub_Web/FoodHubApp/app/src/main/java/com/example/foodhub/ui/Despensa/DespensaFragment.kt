package com.example.foodhub.ui.Despensa

import Producto
import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodhub.R
import com.example.foodhub.Scanner.CustomScannerActivity
import com.example.foodhub.databinding.FragmentDespensaBinding
import com.example.foodhub.modelo.ProductoViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.util.Calendar
import java.util.Date

class DespensaFragment : Fragment() {

    private var _binding: FragmentDespensaBinding? = null
    private val binding get() = _binding!!
    private val productoViewModel: ProductoViewModel by viewModels()
    private lateinit var adapter: ExpandableProductAdapter
    private lateinit var barcodeLauncher: ActivityResultLauncher<ScanOptions>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDespensaBinding.inflate(inflater, container, false)
        setupBarcodeLauncher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupScanButton()
        setupSwipeRefresh()
    }

    private fun setupBarcodeLauncher() {
        barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
            result.contents?.let { barcode ->
                // Maneja el escaneo y obtiene los datos del producto
                handleBarcodeScanned(barcode)
                binding.barcodeInfo.apply {
                    text = getString(R.string.scanned_barcode, barcode)
                    visibility = View.VISIBLE
                }
            } ?: showScanCancelled()
        }
    }

    private fun setupRecyclerView() {
        adapter = ExpandableProductAdapter(mutableListOf()).apply {
            onItemClick = { producto ->
                ProductoDetalleDialog.newInstance(producto)
                    .show(childFragmentManager, "ProductoDetalleDialog")
            }
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = this@DespensaFragment.adapter
        }
    }

    private fun setupObservers() {
        productoViewModel.productos.observe(viewLifecycleOwner) { productos ->
            productos?.let {
                adapter.updateProducts(it)
                binding.emptyState.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                // Forzar actualización del RecyclerView
                adapter.notifyDataSetChanged()
            }
        }

        productoViewModel.producto.observe(viewLifecycleOwner) { producto ->
            producto?.let {
                showDatePickerDialog(it)
                productoViewModel.resetProductState()
            }
        }

        productoViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_LONG).show()
        }

        productoViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            productoViewModel.cargarProductosDeAPI()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupScanButton() {
        binding.btnScan.setOnClickListener {
            checkCameraPermission()
        }
    }

    private fun handleBarcodeScanned(barcode: String) {
        // Llama al método de ViewModel para obtener el producto por código de barras
        productoViewModel.fetchProductByBarcode(barcode)

        // Observa el LiveData para saber cuándo se han cargado los datos
        productoViewModel.producto.observe(viewLifecycleOwner) { producto ->
            producto?.let {
                // Si el producto es encontrado, muestra el picker de fecha
                showDatePickerDialog(it)
            } ?: showProductNotFound()
        }
    }

    private fun showDatePickerDialog(product: Producto) {
        product.fechaCad = null
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val fechaMillis = Calendar.getInstance().apply {
                    set(year, month, day, 0, 0)
                }.timeInMillis

                // Actualiza la fecha de caducidad del producto
                val productoActualizado = product.copy(fechaCad = Date(fechaMillis))

                // Luego guarda el producto actualizado en la API
                productoViewModel.guardarProductoEnAPI(productoActualizado)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showScanCancelled() {
        Toast.makeText(context, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
    }

    private fun showProductNotFound() {
        Toast.makeText(context, "Producto no encontrado en la base de datos", Toast.LENGTH_SHORT).show()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startScan()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startScan() else Toast.makeText(
            context,
            "Permiso de cámara requerido",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun startScan() {
        ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            setPrompt(getString(R.string.scan_prompt))
            setCameraId(0)
            setBeepEnabled(false)
            setOrientationLocked(false)
            setCaptureActivity(CustomScannerActivity::class.java)
        }.let { barcodeLauncher.launch(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

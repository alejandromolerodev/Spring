package org.molerodev.foodhubmvc.controller;

import org.modelmapper.ModelMapper;
import org.molerodev.foodhubmvc.model.ProductoDTO;
import org.molerodev.foodhubmvc.service.ApiRestSingleton;
import org.molerodev.foodhubmvc.service.BarcodeScannerService;
import org.molerodev.foodhubmvc.service.OpenFoodFactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/foodhub")
public class ProductoController {

    @Autowired
    private ApiRestSingleton apiRestSingleton;

    @Autowired
    private BarcodeScannerService barcodeScannerService;

    @Autowired
    private OpenFoodFactsService openFoodFactsService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public String Bienvenida() {
        return "bienvenida";
    }

    @GetMapping("/despensa")
    public String despensa(Model model) {
        // URL base de la API
        String apiUrl = "http://localhost:8080/foodhub"; // Ajusta según sea necesario
        String endpoint = ""; // Endpoint para obtener todos los productos (getAll)

        // Hacer la llamada a la API
        List<ProductoDTO> productos = apiRestSingleton.getProductoFromApi(apiUrl, endpoint);

        // Agregar los productos al modelo
        model.addAttribute("productos", productos);

        // Retornar la vista correspondiente
        return "despensa"; // Asegúrate de que esta vista exista
    }

    @GetMapping("/despensa/nuevo")
    public String nuevoProducto() {
        // Redirige a la vista de la cámara
        return "camera-view";
    }

    @GetMapping("/despensa/scan")
    @ResponseBody // Para devolver directamente el resultado como texto
    public String scanBarcode() {
        return barcodeScannerService.scanBarcode(); // Llama al servicio para escanear el código de barras
    }


    // Endpoint para obtener los detalles de un producto por su código de barras
    @GetMapping("/despensa/producto/{barcode}")
    public ProductoDTO getProductDetails(@PathVariable String barcode) {
        String apiUrl = "http://localhost:8080/foodhub"; // Ajusta según sea necesario
        String endpoint = ""; // Endpoint para crear un nuevo producto
        // Obtener el producto desde Open Food Facts
        ProductoDTO producto = new ProductoDTO();
        modelMapper.map(openFoodFactsService.getProductByBarcode(barcode),producto);
        apiRestSingleton.postProductoToApi(apiUrl,endpoint,producto);

        if (producto != null) {
            System.out.println(producto.getNombre()+" - "+ producto.getNutriScore() +" - "+ producto.getCategoria());
            return producto; // Retorna el producto si se encontró
        } else {
            return null; // Retorna 404 si no se encontró
        }
    }


}
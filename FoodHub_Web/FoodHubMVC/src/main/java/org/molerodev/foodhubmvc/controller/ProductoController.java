package org.molerodev.foodhubmvc.controller;

import org.modelmapper.ModelMapper;
import org.molerodev.foodhubmvc.model.ProductoDTO;
import org.molerodev.foodhubmvc.service.ApiRestSingleton;
import org.molerodev.foodhubmvc.service.BarcodeScannerService;
import org.molerodev.foodhubmvc.service.OpenFoodFactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        String apiUrl = "http://localhost:8080/foodhub";
        String endpoint = "";

        List<ProductoDTO> productos = apiRestSingleton.getProductoFromApi(apiUrl, endpoint);
        model.addAttribute("productos", productos);

        return "despensa";
    }

    @GetMapping("/despensa/nuevo")
    public String nuevoProducto() {
        return "camera-view";
    }

    @GetMapping("/despensa/scan")
    @ResponseBody
    public String scanBarcode() {
        return barcodeScannerService.scanBarcode();
    }

    @GetMapping("/despensa/producto/{barcode}")
    public ProductoDTO getProductDetails(@PathVariable String barcode) {
        String apiUrl = "http://localhost:8080/foodhub";
        String endpoint = "";

        ProductoDTO producto = new ProductoDTO();
        modelMapper.map(openFoodFactsService.getProductByBarcode(barcode), producto);
        apiRestSingleton.postProductoToApi(apiUrl, endpoint, producto);

        if (producto != null) {
            System.out.println(producto.getNombre() + " - " + producto.getNutriScore() + " - " + producto.getCategoria());
            return producto;
        } else {
            return null;
        }
    }

    @PostMapping("/despensa/producto/{id}")
    public String deleteProduct(@PathVariable Long id) {
        String apiUrl = "http://localhost:8080/foodhub";
        String endpoint = "/" + id;
        apiRestSingleton.deleteProductoFromApi(apiUrl, endpoint);
        return "redirect:/foodhub/despensa";
    }

    @PostMapping("/despensa/producto/{id}/actualizarFecha")
public String actualizarFechaCaducidad(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate nuevaFecha) {
    String apiUrl = "http://localhost:8080/foodhub";
    String endpoint = "/" + id;

    // Obtener el producto desde la API usando el ID
    ProductoDTO producto = apiRestSingleton.getProductoByIdFromApi(apiUrl, endpoint, id);

    if (producto != null) {
        // Actualizamos la fecha de caducidad del producto
        producto.setFechaCad(nuevaFecha);

        // Enviar la actualización a la API usando el método PUT
        apiRestSingleton.putProductoToApi(apiUrl, endpoint, producto);

        // Redirigir de vuelta a la lista de productos
        return "redirect:/foodhub/despensa";
    }

    // Si el producto no existe, redirigir a la lista de productos
    return "redirect:/foodhub/despensa";
}

}
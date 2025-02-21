package org.molerodev.foodhubmvc.controller;

import org.molerodev.foodhubmvc.model.ProductoDTO;
import org.molerodev.foodhubmvc.service.ApiRestSingleton;
import org.molerodev.foodhubmvc.service.BarcodeScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/foodhub")
public class ProductoController {

    @Autowired
    private ApiRestSingleton apiRestSingleton;

    @Autowired
    private BarcodeScannerService barcodeScannerService;

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
        List<ProductoDTO> productos = apiRestSingleton.getDataFromApi(apiUrl, endpoint);

        // Agregar los productos al modelo
        model.addAttribute("productos", productos);

        // Retornar la vista correspondiente
        return "despensa"; // Asegúrate de que esta vista exista
    }

    @GetMapping("/despensa/nuevo")
    public String nuevoProducto(Model model) {
        // Redirige a la vista de la cámara
        return "camera-view";
    }

    @GetMapping("/despensa/scan")
    @ResponseBody // Para devolver directamente el resultado como texto
    public String scanBarcode() {
        return barcodeScannerService.scanBarcode(); // Llama al servicio para escanear el código de barras
    }
}
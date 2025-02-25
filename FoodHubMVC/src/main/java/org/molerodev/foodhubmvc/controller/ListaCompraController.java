package org.molerodev.foodhubmvc.controller;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 24/2/25
 */

import org.molerodev.foodhubmvc.model.ListaCompraDTO;
import org.molerodev.foodhubmvc.service.ApiRestSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/foodhub")
public class ListaCompraController {

    @Autowired
    private ApiRestSingleton apiRestSingleton;

    @GetMapping("/listacompra")
    public String lista(Model model) {
        // URL base de la API
        String apiUrl = "http://localhost:8080/foodhub/listacompra"; // Ajusta según sea necesario
        String endpoint = ""; // Endpoint para obtener todos los productos (getAll)

        // Hacer la llamada a la API
        List<ListaCompraDTO> listas = apiRestSingleton.getDataFromApi(apiUrl, endpoint);

        // Agregar los productos al modelo
        model.addAttribute("lista", listas);

        // Retornar la vista correspondiente
        return "listacompra"; // Asegúrate de que esta vista exista
    }


    @PostMapping("/listacompra")
    public String agregarLista(@ModelAttribute ListaCompraDTO listaCompraDTO) {
        String apiUrl = "http://localhost:8080/foodhub/listacompra"; // Ajusta según sea necesario
        String endpoint = "";

        apiRestSingleton.postDataToApi(apiUrl, endpoint, listaCompraDTO, ListaCompraDTO.class);

        return "redirect:/foodhub/listacompra";
    }


}

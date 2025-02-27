package org.molerodev.foodhubmvc.controller;

import org.molerodev.foodhubmvc.model.ListaCompraDTO;
import org.molerodev.foodhubmvc.service.ApiRestSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<ListaCompraDTO> listas = apiRestSingleton.getLCFromApi(apiUrl, endpoint);

        // Agregar los productos al modelo
        model.addAttribute("lista", listas);

        // Retornar la vista correspondiente
        return "listacompra"; // Asegúrate de que esta vista exista
    }

    @GetMapping("/listacompra/nueva")
    public String formulariolista() {
        return "listacompranueva";
    }

    @PostMapping("/listacompra/nueva")
    public String agregarLista(@ModelAttribute ListaCompraDTO listaCompraDTO) {
        String apiUrl = "http://localhost:8080/foodhub/listacompra"; // Ajusta según sea necesario
        String endpoint = "";

        apiRestSingleton.postLCToApi(apiUrl, endpoint, listaCompraDTO);

        return "redirect:/foodhub/listacompra";
    }

    @GetMapping("/listacompra/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable Long id, Model model) {
        // Llamar al servicio para obtener la lista de compra por id
        String apiUrl = "http://localhost:8080/foodhub/listacompra";
        String endpoint = "/" + id;
        ListaCompraDTO listaCompraDTO = apiRestSingleton.getLCforIDFromApi(apiUrl, endpoint, id);

        // Agregar la lista al modelo
        model.addAttribute("lista", listaCompraDTO);

        // Retornar la vista del formulario de actualización
        return "formularioActualizarlista";
    }




    @PostMapping("/listacompra/actualizar/{id}")
    public String actualizarLista(
            @PathVariable Long id,
            @ModelAttribute ListaCompraDTO listaCompraDTO) {

        // Aquí, `listaCompraDTO` tendrá los cambios del estado de los productos
        String apiUrl = "http://localhost:8080/foodhub/listacompra";
        String endpoint = "/" + id; // Asegúrate de que el endpoint coincida con el API

        // Realizamos la actualización mediante la API
        apiRestSingleton.actualizarLC(apiUrl, endpoint, listaCompraDTO);

        // Redirigir a la página de listas de compra después de la actualización
        return "redirect:/foodhub/listacompra";
    }


}

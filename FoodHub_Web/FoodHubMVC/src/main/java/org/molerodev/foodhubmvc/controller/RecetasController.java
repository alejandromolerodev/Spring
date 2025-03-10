package org.molerodev.foodhubmvc.controller;

import org.molerodev.foodhubmvc.model.Hit;
import org.molerodev.foodhubmvc.service.EdamamAPIClient;
import org.molerodev.foodhubmvc.model.RecetasResponse;
import org.molerodev.foodhubmvc.model.Recipe;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/foodhub")
public class RecetasController {


    // Utilizamos el Singleton
    private final EdamamAPIClient edamamAPIClient = EdamamAPIClient.getInstance();

    @GetMapping("/recetas")
    public String receta(){
        return "recetas";
    }
    @GetMapping("/buscar-recetas")
    public String buscarRecetas(@RequestParam("ingrediente") String ingrediente, Model model) {
        // Obtener las respuestas de la API
        RecetasResponse recetasResponse = edamamAPIClient.obtenerRecetasList(ingrediente);

        // Acceder a la lista de "hits" y extraer las recetas
        List<Recipe> recetas = recetasResponse.getHits().stream()
                .map(Hit::getRecipe)  // Extraer el objeto Recipe de cada Hit
                .collect(Collectors.toList()); // Convertir a lista de recetas

        // Agregar las recetas al modelo
        model.addAttribute("recetas", recetas);

        // Devolver la vista
        return "recetas"; // nombre del archivo recetas.html
    }
}

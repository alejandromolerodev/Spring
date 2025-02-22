package org.molerodev.foodhubmvc.service;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 22/2/25
 */

import org.molerodev.foodhubmvc.model.CategoriaDTO;
import org.molerodev.foodhubmvc.model.NutriScore;
import org.molerodev.foodhubmvc.model.ProductoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Service
public class OpenFoodFactsService {

    private static final String OPEN_FOOD_FACTS_API_URL = "https://world.openfoodfacts.org/api/v0/product/";

    public ProductoDTO getProductByBarcode(String barcode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = OPEN_FOOD_FACTS_API_URL + barcode + ".json";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> body = response.getBody();
        System.out.println("Respuesta de la API: " + body); // Imprime la respuesta

        if (body == null || !"product found".equalsIgnoreCase((String) body.get("status_verbose"))) {
            return null; // No se encontró el producto
        }

        Map<String, Object> product = (Map<String, Object>) body.get("product");

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setCodigoBarras(barcode);
        productoDTO.setNombre((String) product.get("product_name"));
        productoDTO.setUrl_image((String) product.get("image_url"));
        productoDTO.setEmpNombre((String) product.get("brands"));
        productoDTO.setPeso((String) product.get("quantity"));

        // Inicializar y asignar la categoría
        if (product.containsKey("categories")) {
            String categories = (String) product.get("categories");
            if (categories != null && !categories.isEmpty()) {
                CategoriaDTO categoriaDTO = new CategoriaDTO(); // Inicializar el objeto CategoriaDTO
                categoriaDTO.setNombre(categories.split(",")[0].trim()); // Tomar la primera categoría
                productoDTO.setCategoria(categoriaDTO); // Asignar la categoría al producto
            }
        }

        // Obtener y asignar el NutriScore
        String nutriScore = (String) product.get("nutrition_grades");
        if (nutriScore != null && !nutriScore.isEmpty()) {
            try {
                productoDTO.setNutriScore(NutriScore.valueOf(nutriScore.toUpperCase())); // Convertir a enum
            } catch (IllegalArgumentException e) {
                // Si el valor no coincide con A, B, C, D, E, asignar UNKNOWN
                productoDTO.setNutriScore(NutriScore.UNKNOWN);
            }
        } else {
            productoDTO.setNutriScore(NutriScore.UNKNOWN); // Si no hay NutriScore, asignar UNKNOWN
        }

        System.out.println("ProductoDTO: " + productoDTO); // Imprime el objeto ProductoDTO

        return productoDTO;
    }
}
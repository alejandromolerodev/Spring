package org.molerodev.foodhubmvc.service;

import org.molerodev.foodhubmvc.model.ProductoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@Component
public class ApiRestSingleton {

    private static ApiRestSingleton instance;
    private RestTemplate restTemplate;

    // Constructor privado para asegurar que la instancia solo se crea una vez
    private ApiRestSingleton() {
        this.restTemplate = new RestTemplate();
    }

    // Método para obtener la instancia del Singleton
    public static synchronized ApiRestSingleton getInstance() {
        if (instance == null) {
            instance = new ApiRestSingleton();
        }
        return instance;
    }

    // Método para realizar la consulta GET a la API y obtener los productos
    public List<ProductoDTO> getDataFromApi(String apiUrl, String endpoint) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .path(endpoint)
                .toUriString();
        // Usamos RestTemplate para convertir directamente la respuesta a una lista de ProductoDTO
        return restTemplate.getForObject(url, List.class);
    }

    // Método para realizar el POST a la API
    public String postDataToApi(String apiUrl, String endpoint, ProductoDTO requestBody) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .path(endpoint)
                .toUriString();
        return restTemplate.postForObject(url, requestBody, String.class);
    }
}

package org.molerodev.foodhubmvc.service;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 21/2/25
 */

import org.molerodev.foodhubmvc.model.RecetasResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class EdamamAPIClient {

    // Tu app_id y app_key proporcionados
    private static final String APP_ID = "d764f935";   // Tu app_id
    private static final String APP_KEY = "8c490a141b3d280b00072140b9872a8c"; // Tu app_key
    private static final String API_URL = "https://api.edamam.com/search";
    private static final String USER_ID = "Alejandromolero";  // Asegúrate de usar tu propio userID

    private static EdamamAPIClient instance;

    private EdamamAPIClient() {}

    public static EdamamAPIClient getInstance() {
        if (instance == null) {
            instance = new EdamamAPIClient();
        }
        return instance;
    }

    public RecetasResponse obtenerRecetasList(String ingrediente) {
        // Construir la URL con parámetros de búsqueda
        String url = UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("q", ingrediente)       // Ingrediente a buscar
                .queryParam("app_id", APP_ID)       // Añadir app_id
                .queryParam("app_key", APP_KEY)     // Añadir app_key
                .toUriString();

        // Crear los encabezados HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Edamam-Account-User", USER_ID);  // Añadir el userID al encabezado

        // Crear el RestTemplate y la solicitud con los encabezados
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecetasResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, new org.springframework.http.HttpEntity<>(headers), RecetasResponse.class);

        return response.getBody();
    }
}

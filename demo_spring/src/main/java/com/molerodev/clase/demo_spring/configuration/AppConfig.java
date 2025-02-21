/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:54:49
 * @ Modified time: 2025-01-24 19:55:13
 */

package com.molerodev.clase.demo_spring.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Esta anotación indica que es una clase de configuración de Spring
public class AppConfig {

    @Bean  // Esto indica que Spring gestionará esta instancia de ModelMapper
    public ModelMapper modelMapper() {
        return new ModelMapper();  // Retorna una instancia de ModelMapper
    }
}

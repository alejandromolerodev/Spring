package org.molerodev.foodhubmvc.configuration;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 22/2/25
 */

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

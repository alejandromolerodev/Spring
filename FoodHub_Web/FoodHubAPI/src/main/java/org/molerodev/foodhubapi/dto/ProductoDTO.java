package org.molerodev.foodhubapi.dto;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.molerodev.foodhubapi.entity.Categoria;
import org.molerodev.foodhubapi.model.NutriScore;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ProductoDTO {


    private Long id;

    private String url_image;

    private String nombre;

    private String empNombre;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate fechaCad;

    private NutriScore nutriScore;

    private Categoria categoria;

    private String peso;

    private String codigoBarras;

}

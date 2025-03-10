package org.molerodev.foodhubmvc.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 21/2/25
 */
@Setter
@Getter
public class ProductoDTO {

    private Long id;
    private String url_image;
    private String nombre;
    private String empNombre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCad;

    private NutriScore nutriScore;
    private CategoriaDTO categoria;
    private String peso;
    private String codigoBarras;


}

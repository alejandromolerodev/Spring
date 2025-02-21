package org.molerodev.foodhubmvc.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 21/2/25
 */
public class ProductoDTO {


    private Long id;

    private String url_image;

    private String nombre;

    private String empNombre;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date fechaCad;

    private NutriScore nutriScore;

    private CategoriaDTO categoria;

    private String peso;

    private String codigoBarras;

}

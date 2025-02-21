/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 10:08:54
 * @ Modified time: 2025-02-15 13:13:07
 */


package com.molerodev.clase.demo_spring.dto;

import java.util.List;

import com.molerodev.clase.demo_spring.entity.Venta;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ArticuloDTO {

   
    private Long idArt;

    private String nombre;

    private double precio;
    
    private String descripcion;

    private List<Venta> ventas;


}

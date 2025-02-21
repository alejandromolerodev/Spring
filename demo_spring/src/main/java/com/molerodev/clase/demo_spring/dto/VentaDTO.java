/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 11:36:58
 * @ Modified time: 2025-02-15 14:19:07
 */

package com.molerodev.clase.demo_spring.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.molerodev.clase.demo_spring.entity.Articulo;
import com.molerodev.clase.demo_spring.entity.Cliente;
import com.molerodev.clase.demo_spring.entity.Empleado;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VentaDTO {

   
    private Long idVenta;

 
    private Empleado empleado;

    private Articulo articulo;

    private Cliente cliente;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVenta;

    private double precioVenta;
   
    
}

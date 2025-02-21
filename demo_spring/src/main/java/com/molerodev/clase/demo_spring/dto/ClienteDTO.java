/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 12:13:44
 * @ Modified time: 2025-02-15 13:13:28
 */

package com.molerodev.clase.demo_spring.dto;

import java.util.List;

import com.molerodev.clase.demo_spring.entity.Venta;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ClienteDTO {

   
    private Long idCli;

    private String nombre;

    private String telefono;

    private List<Venta> ventas;

}

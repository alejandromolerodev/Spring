/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 08:39:24
 * @ Modified time: 2025-02-15 13:14:09
 */

package com.molerodev.clase.demo_spring.dto;

import java.util.ArrayList;
import java.util.List;

import com.molerodev.clase.demo_spring.entity.Empleado;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DepartamentoDTO {


    private Long id;

    private String nombre;

    private String localidad;

    private List<Empleado> empleados = new ArrayList<>();


    public DepartamentoDTO(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;

    }
    

    
}

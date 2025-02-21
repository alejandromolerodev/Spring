/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 08:50:11
 * @ Modified time: 2025-02-15 13:47:44
 */

package com.molerodev.clase.demo_spring.dto;

import java.time.LocalDate;
import java.util.List;


import com.molerodev.clase.demo_spring.entity.Departamento;
import com.molerodev.clase.demo_spring.entity.Direccion;
import com.molerodev.clase.demo_spring.entity.Venta;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class EmpleadoDTO {

    
    private Long id;

    private String nombre;
    private float sueldo;


    private LocalDate fechanac;

    private Departamento departamento;

    private List<Venta> ventas;

    private Direccion direccion;


    public EmpleadoDTO(String nombre, float sueldo, LocalDate fecha, Departamento departamento, Direccion direccion) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.fechanac = fecha;
        this.departamento = departamento;
        this.direccion = direccion;
    }

}

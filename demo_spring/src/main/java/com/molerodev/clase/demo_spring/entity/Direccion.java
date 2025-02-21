/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-23 17:47:19
 * @ Modified time: 2025-02-15 14:19:44
 */

package com.molerodev.clase.demo_spring.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Direccion {


    private String direccion;

    private String localidad;

    private String CP;


    public Direccion(String direccion, String localidad, String CP, Empleado empleado) {
        this.direccion = direccion;
        this.localidad = localidad;
        this.CP = CP;
    }
}

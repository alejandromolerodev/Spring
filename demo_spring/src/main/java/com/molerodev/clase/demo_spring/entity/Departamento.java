/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:51:00
 * @ Modified time: 2025-02-15 14:19:39
 */

package com.molerodev.clase.demo_spring.entity;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name= "AMT_DEPARTAMENTOS")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPT")
    private Long id;

    @Column(name = "NOMBRE_DEPT", unique = true, nullable = false)
    private String nombre;

    @Column(name = "LOC_DEPT")
    private String localidad;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Empleado> empleados = new ArrayList<>();


    public Departamento(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;

    }

}

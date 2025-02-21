/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-27 11:18:01
 * @ Modified time: 2025-02-15 14:19:53
 */

package com.molerodev.clase.demo_spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "AMT_EMPLEADOS") // El correo electrónico debe ser único
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLEADO")
    private Long id;

    private String nombre;
    private float sueldo;


    @Column(name = "FECHA_NACIMIENTO")
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Asegura que Spring pueda convertir el valor del formulario al formato LocalDate
    private LocalDate fechanac;

    @ManyToOne
    @JoinColumn(name = "ID_DEPARTAMENTO")
    private Departamento departamento;

    @OneToMany(mappedBy = "empleado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Venta> ventas;

    @Embedded
    private Direccion direccion;


    public Empleado(String nombre, float sueldo, LocalDate fecha, Departamento departamento, Direccion direccion) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.fechanac = fecha;
        this.departamento = departamento;
        this.direccion = direccion;
    }
}

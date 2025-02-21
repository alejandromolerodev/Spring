/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:50:50
 * @ Modified time: 2025-02-15 14:19:58
 */

package com.molerodev.clase.demo_spring.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "empleado")  // Esta columna debe existir en tu base de datos
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "idArticulo")  // Esta columna debe coincidir con la de "idArt" en la tabla Articulo
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "idCli")  // Esta columna debe existir en tu base de datos
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVenta;

    private double precioVenta;
}

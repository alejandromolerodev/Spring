/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:51:09
 * @ Modified time: 2025-02-15 14:19:18
 */

package com.molerodev.clase.demo_spring.entity;

import java.util.List;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArt;

    private String nombre;

    private double precio;
    
    private String descripcion;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)  // Asegúrate que "articulo" sea el nombre del atributo en la entidad Venta
    private List<Venta> ventas;



}

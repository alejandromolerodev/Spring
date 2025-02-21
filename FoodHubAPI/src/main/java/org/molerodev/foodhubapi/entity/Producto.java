package org.molerodev.foodhubapi.entity;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.molerodev.foodhubapi.model.NutriScore;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Imagen")
    private String url_image;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Nombre_Empresa")
    private String empNombre;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date fechaCad;

    @Enumerated(EnumType.STRING)
    private NutriScore nutriScore;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Categoría")
    private Categoria categoria;

    @Column(name = "Peso")
    private String peso;

    @Column(name = "Código de barras")
    private String codigoBarras;



}

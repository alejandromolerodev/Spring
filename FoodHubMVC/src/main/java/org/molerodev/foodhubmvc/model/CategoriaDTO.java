package org.molerodev.foodhubmvc.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 21/2/25
 */
@Getter
@Setter
public class CategoriaDTO {

    private Long id;

    private String nombre;

    private CategoriaDTO subcategoria;
}

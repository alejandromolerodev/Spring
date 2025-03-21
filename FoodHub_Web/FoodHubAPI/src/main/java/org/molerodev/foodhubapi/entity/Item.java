package org.molerodev.foodhubapi.entity;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 26/2/25
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.molerodev.foodhubapi.model.Estado;

@Setter
@Getter
@Embeddable
public class Item {


    private String nombre;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}

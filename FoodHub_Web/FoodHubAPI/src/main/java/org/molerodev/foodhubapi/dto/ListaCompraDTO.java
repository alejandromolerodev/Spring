package org.molerodev.foodhubapi.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.molerodev.foodhubapi.entity.Item;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 24/2/25
 */
@Setter
@Getter
public class ListaCompraDTO {

    private Long id;

    private String titulo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private List<Item> items;
}

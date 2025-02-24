package org.molerodev.foodhubapi.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate fecha;

    private List<String> items;
}

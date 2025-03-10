package org.molerodev.foodhubmvc.model;
/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 21/2/25
 */

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecetasResponse {
    private List<Hit> hits;

}

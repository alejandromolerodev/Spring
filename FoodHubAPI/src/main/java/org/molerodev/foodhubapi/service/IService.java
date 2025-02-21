package org.molerodev.foodhubapi.service;

import org.molerodev.foodhubapi.dto.ProductoDTO;
import org.molerodev.foodhubapi.entity.Producto;

import java.util.List;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */
public interface IService<T,K> {

    List<K> getAll();
    K getProducto(Long id);

    T save(T t);

    Producto updateProducto(Long id, Producto productoDetails);

    void delete(Long id);


}

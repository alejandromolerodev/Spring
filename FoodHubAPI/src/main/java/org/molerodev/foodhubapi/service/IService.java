package org.molerodev.foodhubapi.service;

import org.molerodev.foodhubapi.dto.ProductoDTO;
import org.molerodev.foodhubapi.entity.Producto;

import java.time.LocalDate;
import java.util.List;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */
public interface IService<T,K> {

    List<K> getAll();
    K get(Long id);

    T save(K k);

    T updateProducto(Long id, K tDetails);

    void delete(Long id);


    T convertToEntity(K k);

    K convertToDTO(T t);

    T updateFecha(Long id, LocalDate fecha);


}

/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-02-03 19:03:12
 * @ Modified time: 2025-02-13 21:25:27
 */

package com.molerodev.clase.demo_spring.service;

import java.util.List;

public interface IService<T, D> {

    T save(T entity);

    T findById(Long id);

    List<T> findAll();

    void deleteById(Long id);

    void deleteAll();

    T update(T entity);

    D convertToDTO(T entity);

    T convertToEntity(D dto);




}

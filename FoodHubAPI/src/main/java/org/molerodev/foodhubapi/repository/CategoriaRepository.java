package org.molerodev.foodhubapi.repository;

import org.molerodev.foodhubapi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}

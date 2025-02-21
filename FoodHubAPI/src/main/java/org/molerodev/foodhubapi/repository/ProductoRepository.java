package org.molerodev.foodhubapi.repository;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */

import org.molerodev.foodhubapi.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.lang.model.element.ModuleElement;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {


}

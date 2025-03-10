package org.molerodev.foodhubapi.repository;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 24/2/25
 */

import org.molerodev.foodhubapi.entity.ListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaCompraRepository extends JpaRepository<ListaCompra,Long> {
}

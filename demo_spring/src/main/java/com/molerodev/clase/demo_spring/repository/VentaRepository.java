/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:59:53
 * @ Modified time: 2025-02-11 11:20:26
 */

package com.molerodev.clase.demo_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.molerodev.clase.demo_spring.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

}

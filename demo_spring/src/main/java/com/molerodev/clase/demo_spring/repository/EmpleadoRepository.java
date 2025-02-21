/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:59:52
 * @ Modified time: 2025-02-15 13:44:05
 */

package com.molerodev.clase.demo_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.molerodev.clase.demo_spring.entity.Empleado;



@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long>{

}

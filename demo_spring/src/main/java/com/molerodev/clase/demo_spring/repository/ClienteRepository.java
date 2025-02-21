/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:59:47
 * @ Modified time: 2025-02-11 11:21:53
 */

package com.molerodev.clase.demo_spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.molerodev.clase.demo_spring.entity.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNombre(String nombre);

    Optional<Cliente> findByTelefono(String telefono);
}

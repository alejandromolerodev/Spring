package com.molerodev.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.molerodev.crud.model.Usuario;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

      // Buscar usuarios por nombre
      List<Usuario> findByNombre(String nombre);

      // Buscar usuarios por email
      List<Usuario> findByEmail(String email);
  
      // Buscar usuarios por edad
      List<Usuario> findByEdad(int edad);

}

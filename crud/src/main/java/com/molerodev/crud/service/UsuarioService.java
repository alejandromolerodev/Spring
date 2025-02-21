package com.molerodev.crud.service;

import java.util.List;



import com.molerodev.crud.model.Usuario;



public interface UsuarioService {

    // Método para guardar un nuevo usuario o actualizar uno existente
    void save(Usuario usuario);

    // Método para actualizar un usuario existente
    void update(Usuario usuario);

    // Método para eliminar un usuario por su ID
    void deletebyId(Long id);

    // Método para obtener todos los usuarios
    List<Usuario> findAll();

    // Método para buscar un usuario por su ID
    Usuario findById(Long id);

    // Método para buscar usuarios por su email
    List<Usuario> findByEmail(String email);

    // Método para buscar usuarios por su nombre
    List<Usuario> findByNombre(String nombre);

    // Método para buscar usuarios por su edad
    List<Usuario> findByEdad(int edad);
}


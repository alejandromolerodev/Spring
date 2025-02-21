package com.molerodev.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molerodev.crud.model.Usuario;
import com.molerodev.crud.repositories.UsuarioRepository;

@Service
public class UsuarioService_Impl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void save(Usuario usuario) {
        // JPA se encarga de generar el id automáticamente cuando el usuario es nuevo
        usuarioRepository.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            // Verificamos si el usuario existe en la base de datos
            Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(usuario.getId());
            if (usuarioExistenteOpt.isPresent()) {
                // Recuperamos el usuario existente
                Usuario usuarioExistente = usuarioExistenteOpt.get();
    
                // Actualizamos solo los campos que han cambiado
                usuarioExistente.setId(usuario.getId());
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setEmail(usuario.getEmail());
                usuarioExistente.setEdad(usuario.getEdad());
    
                // Guardamos los cambios
                usuarioRepository.save(usuarioExistente); // Actualiza el usuario
            } else {
                throw new RuntimeException("Usuario no encontrado para actualizar");
            }
        }
    }
    


    @Override
    public void deletebyId(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        // Si el id no existe, devuelve null
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public List<Usuario> findByEdad(int edad) {
        return usuarioRepository.findByEdad(edad);
    }
}

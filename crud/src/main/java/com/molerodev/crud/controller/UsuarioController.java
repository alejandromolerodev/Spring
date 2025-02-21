package com.molerodev.crud.controller;

import com.molerodev.crud.model.Usuario;
import com.molerodev.crud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Controlador para la entidad Usuario
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Método para obtener todos los usuarios o filtrar por nombre
    @GetMapping("/buscar")
    public String getUsuarios(@RequestParam(required = false) String nombre, Model model) {
        List<Usuario> usuarios;
        if (nombre != null && !nombre.isEmpty()) {
            // Buscar usuarios por nombre
            usuarios = usuarioService.findByNombre(nombre);
            if (usuarios.isEmpty()) {
                throw new RuntimeException("No se encontró el usuario");
            }
        } else {
            // Mostrar todos los usuarios si no hay nombre
            usuarios = usuarioService.findAll();
        }
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // Vista usuarios.html con los resultados
    }

    @GetMapping("/nuevo")
    public String formularioAgregar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formulario-usuario"; // Vista formulario-usuario.html
    }

    // Mostrar el formulario para editar un usuario
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        model.addAttribute("usuario", usuario);
        return "formulario-usuario-editar"; // Vista formulario-usuario.html
    }

    // Método para guardar el nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuarios/buscar"; // Redirigir a la lista de usuarios
    }

    // Método para actualizar el usuario
    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        // Actualizar el usuario en la base de datos
        usuarioService.update(usuario);
        return "redirect:/usuarios/buscar"; // Redirigir a la lista de usuarios
    }

    // Método para eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deletebyId(id);
        return "redirect:/usuarios/buscar"; // Redirigir a la página de búsqueda o lista
    }
}

/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-02-04 17:23:05
 * @ Modified time: 2025-02-15 13:40:30
 */


package com.molerodev.clase.demo_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.molerodev.clase.demo_spring.dto.ArticuloDTO;
import com.molerodev.clase.demo_spring.entity.Articulo;
import com.molerodev.clase.demo_spring.service.IService;

@Controller
@RequestMapping("/bienvenida")
public class ArticuloController {

    @Autowired
    private IService<Articulo,ArticuloDTO> iService;


    @GetMapping("/articulos")
    public String mostrarArticulos(Model model) {
        List<ArticuloDTO> articulos = iService.findAll().stream()
                                            .map(articulo -> iService.convertToDTO(articulo))
                                            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("articulos", articulos);
        return "articulos";
    }

    // Método para mostrar el formulario de agregar departamento
    @GetMapping("/articulos/formulario")
    public String showForm(Model model) {
        model.addAttribute("articulo", new ArticuloDTO());
        return "formularioArticulo"; // La vista para el formulario
    }

    @PostMapping("/articulos/post")
    public String crearArticulo(ArticuloDTO articuloDTO) {
        Articulo articulo = iService.convertToEntity(articuloDTO);
        iService.save(articulo);
        return "redirect:/bienvenida/articulos";
    }


    @GetMapping("/articulos/actualizar/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Articulo articulo = iService.findById(id);  // Método para obtener el departamento por ID
        model.addAttribute("articulo", articulo);
        return "formularioActualizarArticulo";  // El nombre del archivo HTML
    }

    @PostMapping("/articulos/actualizar/{id}")
    public String updateArticulo(@PathVariable Long id, @ModelAttribute Articulo articulo) {
        articulo.setIdArt(id);;  // Aseguramos que el ID se mantenga al actualizar
        iService.update(articulo);
        return "redirect:/bienvenida/articulos";  // Redirige a la lista de departamentos
    }


    @GetMapping("/articulos/eliminar/{id}")
    public String deleteArticulo(@PathVariable Long id) {
        iService.deleteById(id);
        return "redirect:/bienvenida/articulos";
    }




}

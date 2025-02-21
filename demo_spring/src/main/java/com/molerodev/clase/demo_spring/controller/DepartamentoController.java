/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 19:00:51
 * @ Modified time: 2025-02-15 13:38:20
 */

package com.molerodev.clase.demo_spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.molerodev.clase.demo_spring.dto.DepartamentoDTO;
import com.molerodev.clase.demo_spring.entity.Departamento;
import com.molerodev.clase.demo_spring.service.IService;

@Controller
@RequestMapping("/bienvenida")
public class DepartamentoController {

    @Autowired
    private IService<Departamento,DepartamentoDTO> iService;
    

    @GetMapping
    public String bienvenida() {
        return "bienvenida";
    }


    @GetMapping("/departamentos")
    public String getAll(Model model) {

        List<DepartamentoDTO> departamentos = iService.findAll().stream()
                                                .map(departamento -> iService.convertToDTO(departamento))
                                                .collect(java.util.stream.Collectors.toList());
        model.addAttribute("departamentos", departamentos);

        return "departamentos";
    }

    // Método para mostrar el formulario de agregar departamento
    @GetMapping("/departamentos/formulario")
    public String showForm(Model model) {
        model.addAttribute("departamento", new DepartamentoDTO());
        return "formularioDepartamento"; // La vista para el formulario
    }

    @PostMapping("/departamentos/post")
    public String addDpto(@ModelAttribute DepartamentoDTO departamentoDTO) {
        Departamento departamento = iService.convertToEntity(departamentoDTO);

        if (departamento.getEmpleados() == null) {
            departamento.setEmpleados(new ArrayList<>());
        }
        iService.save(departamento);

        return "redirect:/bienvenida/departamentos";

    }

    @GetMapping("/departamentos/eliminar/{id}")
    public String deleteDpto(@PathVariable Long id){
        iService.deleteById(id);

        return "redirect:/bienvenida/departamentos";
    }

    @GetMapping("/departamentos/actualizar/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Departamento departamento = iService.findById(id);  // Método para obtener el departamento por ID
        model.addAttribute("departamento", departamento);
        return "formularioActualizarDepartamento";  // El nombre del archivo HTML
    }

    @PostMapping("/departamentos/actualizar/{id}")
    public String updateArticulo(@PathVariable Long id, @ModelAttribute Departamento departamento) {
        departamento.setId(id);;  // Aseguramos que el ID se mantenga al actualizar
        iService.update(departamento);
        return "redirect:/bienvenida/departamentos";  // Redirige a la lista de departamentos
    }

}

/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-31 12:15:16
 * @ Modified time: 2025-02-15 14:18:39
 */



package com.molerodev.clase.demo_spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.molerodev.clase.demo_spring.service.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.molerodev.clase.demo_spring.dto.DepartamentoDTO;
import com.molerodev.clase.demo_spring.dto.EmpleadoDTO;
import com.molerodev.clase.demo_spring.entity.Departamento;
import com.molerodev.clase.demo_spring.entity.Empleado;

@Controller
@RequestMapping("/bienvenida")
public class EmpleadoController {


    @Autowired
    private IService<Empleado,EmpleadoDTO> iService;

    @Autowired
    private IService<Departamento,DepartamentoDTO> depService;

    

    @GetMapping("/empleados")
    public String listarEmpleados(Model model) {
        List<EmpleadoDTO> empleados = iService.findAll().stream()
                                                .map(empleado -> iService.convertToDTO(empleado))
                                                .collect(Collectors.toList());      
        model.addAttribute("empleados", empleados);
        return "empleados";
    }

     // Método para mostrar el formulario de agregar empleado
     @GetMapping("/empleados/formulario")
     public String showForm(Model model) {
         model.addAttribute("empleado", new EmpleadoDTO());
         model.addAttribute("departamento", depService.findAll());
         return "formularioEmpleado"; // La vista para el formulario
     }

    @PostMapping("/empleados/post")
    public String crearEmpleado(@ModelAttribute EmpleadoDTO empleadoDTO) {
        Empleado empleado = iService.convertToEntity(empleadoDTO);
        iService.save(empleado);
        return "redirect:/bienvenida/empleados";
    }

    @GetMapping("/empleados/actualizar/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {

        model.addAttribute("empleado", iService.findById(id));
        model.addAttribute("departamento", depService.findAll());
        return "formularioActualizarEmpleado"; // La vista para el formulario de actualización
    }

    @PostMapping("/empleados/actualizar/{id}")
    public String actualizarEmpleado(@PathVariable Long id, @ModelAttribute Empleado empleado) {
        empleado.setId(id);
        iService.update(empleado);
        return "redirect:/bienvenida/empleados";
    }

    @GetMapping("/empleados/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        iService.deleteById(id);
        return "redirect:/bienvenida/empleados";
    }

    
}

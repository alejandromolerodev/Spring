/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-02-07 11:59:22
 * @ Modified time: 2025-02-15 13:39:22
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

import com.molerodev.clase.demo_spring.dto.ClienteDTO;
import com.molerodev.clase.demo_spring.entity.Cliente;
import com.molerodev.clase.demo_spring.service.IService;

@Controller
@RequestMapping("/bienvenida")
public class ClienteController {

    @Autowired
    private IService<Cliente,ClienteDTO> iService;


    @GetMapping("/clientes")
    public String cliente(Model model){
        List<ClienteDTO> clientes = iService.findAll().stream()
                                                .map(cliente -> iService.convertToDTO(cliente))
                                                .collect(java.util.stream.Collectors.toList());
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    // Método para mostrar el formulario de agregar departamento
    @GetMapping("/clientes/formulario")
    public String showForm(Model model) {
        model.addAttribute("cliente", new ClienteDTO());
        return "formularioCliente"; // La vista para el formulario
    }

    @PostMapping("/clientes/post")
    public String crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = iService.convertToEntity(clienteDTO);
        iService.save(cliente);
        return "redirect:/bienvenida/clientes";
    }

    @GetMapping("/clientes/actualizar/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Cliente cliente = iService.findById(id);  // Método para obtener el departamento por ID
        model.addAttribute("cliente", cliente);
        return "formularioActualizarCliente";  // El nombre del archivo HTML
    }

    @PostMapping("/clientes/actualizar/{id}")
    public String updateArticulo(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        cliente.setIdCli(id);;  // Aseguramos que el ID se mantenga al actualizar
        iService.update(cliente);
        return "redirect:/bienvenida/clientes";  // Redirige a la lista de departamentos
    }




    
}

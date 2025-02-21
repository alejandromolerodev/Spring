/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-31 12:40:19
 * @ Modified time: 2025-02-15 14:18:56
 */

package com.molerodev.clase.demo_spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.molerodev.clase.demo_spring.dto.ArticuloDTO;
import com.molerodev.clase.demo_spring.dto.ClienteDTO;
import com.molerodev.clase.demo_spring.dto.EmpleadoDTO;
import com.molerodev.clase.demo_spring.dto.VentaDTO;
import com.molerodev.clase.demo_spring.entity.Articulo;
import com.molerodev.clase.demo_spring.entity.Cliente;
import com.molerodev.clase.demo_spring.entity.Empleado;
import com.molerodev.clase.demo_spring.entity.Venta;
import com.molerodev.clase.demo_spring.service.IService;
import com.molerodev.clase.demo_spring.service.QRService;

@Controller
@RequestMapping("/bienvenida")
public class VentaController {

    @Autowired
    private IService<Venta, VentaDTO> iService;

    @Autowired
    private IService<Empleado, EmpleadoDTO> emplService;

    @Autowired
    private IService<Cliente, ClienteDTO> cliService;

    @Autowired
    private IService<Articulo, ArticuloDTO> artService;
    @Autowired
    private QRService qrService;

    @GetMapping("/ventas")
    public String listarVentas(Model model) {
        List<VentaDTO> ventas = iService.findAll().stream()
                .map(venta -> iService.convertToDTO(venta))
                .collect(Collectors.toList());

        // Pasar las ventas a la vista
        model.addAttribute("ventas", ventas);
        return "ventas"; // Nombre de la vista
    }

    @GetMapping("/ventas/{idVenta}/qr")
    @ResponseBody
    public byte[] getQRCode(@PathVariable Long idVenta) throws Exception {
        // Obtener la venta y generar el código QR basado en la información de la venta
        Venta venta = iService.findById(idVenta);
        VentaDTO ventaDTO = iService.convertToDTO(venta);

        if (ventaDTO == null) {
            throw new IllegalArgumentException("Venta no encontrada");
        }

        String qrData = "Venta ID: " + ventaDTO.getIdVenta() +
                ", Artículo: " + ventaDTO.getArticulo().getNombre() +
                ", Empleado: " + ventaDTO.getEmpleado().getNombre() +
                ", Cliente: " + ventaDTO.getCliente().getNombre() +
                ", Precio: " + ventaDTO.getPrecioVenta() +
                ", Fecha: " + ventaDTO.getFechaVenta();

        // Generar el código QR como imagen
        byte[] qrCode = qrService.generarQRCode(qrData);

        return qrCode;
    }

    @GetMapping("/ventas/formulario")
    public String showForm(Model model) {
        model.addAttribute("venta", new VentaDTO());
        model.addAttribute("empleados", emplService.findAll());
        model.addAttribute("clientes", cliService.findAll());
        model.addAttribute("articulos", artService.findAll());
        return "formularioVenta"; // La vista para el formulario
    }

    @PostMapping("/ventas/post")
    public String crearVenta(@ModelAttribute VentaDTO ventaDTO) {

        Venta venta = iService.convertToEntity(ventaDTO);
        iService.save(venta);
        return "redirect:/bienvenida/ventas";
    }

    @GetMapping("/ventas/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        iService.deleteById(id);
        return "redirect:/bienvenida/ventas";
    }

}

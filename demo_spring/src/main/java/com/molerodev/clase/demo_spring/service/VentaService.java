/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 11:43:45
 * @ Modified time: 2025-02-11 12:26:19
 */

package com.molerodev.clase.demo_spring.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molerodev.clase.demo_spring.dto.VentaDTO;
import com.molerodev.clase.demo_spring.entity.Articulo;
import com.molerodev.clase.demo_spring.entity.Empleado;
import com.molerodev.clase.demo_spring.entity.Venta;
import com.molerodev.clase.demo_spring.repository.ArticuloRepository;
import com.molerodev.clase.demo_spring.repository.EmpleadoRepository;
import com.molerodev.clase.demo_spring.repository.VentaRepository;

@Service
public class VentaService implements IService<Venta,VentaDTO>{

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ModelMapper modelmapper;

    @Override
    public Venta save(Venta venta) {
        // Verificar si el artículo existe
        Articulo articulo = articuloRepository.findById(venta.getArticulo().getIdArt())
                .orElseThrow(() -> new RuntimeException("El artículo con ID " + venta.getArticulo().getIdArt() + " no existe."));
        
        // Verificar si el empleado existe
        Empleado empleado = empleadoRepository.findById(venta.getEmpleado().getId())
                .orElseThrow(() -> new RuntimeException("El empleado con ID " + venta.getEmpleado().getId() + " no existe."));
        
        // Asignar artículo y empleado a la venta
        venta.setArticulo(articulo);
        venta.setEmpleado(empleado);

        // Guardar la venta
        return ventaRepository.save(venta);
    }

    @Override
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAll() {
        ventaRepository.deleteAll();
    }

    @Override
    public Venta update(Venta venta) {
        Venta ventaToUpdate = ventaRepository.findById(venta.getIdVenta())
                .orElseThrow(() -> new RuntimeException("No existe la venta con ID " + venta.getIdVenta()));

        modelmapper.map(venta, ventaToUpdate);

        return ventaRepository.save(ventaToUpdate);
    }

    @Override
    public VentaDTO convertToDTO(Venta venta) {
        return modelmapper.map(venta, VentaDTO.class);
    }

    @Override
    public Venta convertToEntity(VentaDTO dto) {
        return modelmapper.map(dto, Venta.class);
    }


}

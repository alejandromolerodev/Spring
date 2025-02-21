/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-23 18:33:54
 * @ Modified time: 2025-02-15 13:12:24
 */

package com.molerodev.clase.demo_spring.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molerodev.clase.demo_spring.dto.EmpleadoDTO;
import com.molerodev.clase.demo_spring.entity.Empleado;
import com.molerodev.clase.demo_spring.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements IService<Empleado, EmpleadoDTO>{

    @Autowired
    private EmpleadoRepository empleadoRepository;



    @Autowired
    private ModelMapper modelMapper;

  


    @Override
    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado findById(Long aLong) {
        return empleadoRepository.findById(aLong).orElse(null);
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {

        if (empleadoRepository.existsById(aLong)) {
            empleadoRepository.deleteById(aLong);
        }
    }

    @Override
    public void deleteAll() {

        empleadoRepository.deleteAll();
    }

    @Override
    public Empleado update(Empleado empleado) {
        Empleado empleadoToUpdate = empleadoRepository
                .findById(empleado.getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        modelMapper.map(empleado, empleadoToUpdate);

        return empleadoRepository.save(empleadoToUpdate);

    }

    @Override
    public EmpleadoDTO convertToDTO(Empleado empleado) {
    
        // Realizamos la conversión estándar con el mapeo ya configurado
        return modelMapper.map(empleado, EmpleadoDTO.class);
    }

    @Override
    public Empleado convertToEntity(EmpleadoDTO dto) {
      
        return modelMapper.map(dto, Empleado.class);
    }


}

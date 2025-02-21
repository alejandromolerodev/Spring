/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-23 18:33:50
 * @ Modified time: 2025-01-25 14:17:44
 */

package com.molerodev.clase.demo_spring.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molerodev.clase.demo_spring.dto.DepartamentoDTO;
import com.molerodev.clase.demo_spring.entity.Departamento;
import com.molerodev.clase.demo_spring.repository.DepartamentoRepository;


@Service
public class DepartamentoService implements IService<Departamento,DepartamentoDTO>{

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private ModelMapper modelMapper;


    
    
    @Override
    public Departamento save(Departamento departamento) {

        return departamentoRepository.save(departamento);
    }

    @Override
    public Departamento findById(Long aLong) {
        return departamentoRepository.findById(aLong).orElse(null);
    }

    @Override
    public List<Departamento> findAll() {
        return departamentoRepository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {

        if(departamentoRepository.existsById(aLong)){
            departamentoRepository.deleteById(aLong);
        }
    }

    @Override
    public void deleteAll() {
        departamentoRepository.deleteAll();
    }

    @Override
    public Departamento update(Departamento departamento) {
        Departamento departamentoToUpdate = departamentoRepository
        .findById(departamento.getId())
        .orElseThrow(() -> new RuntimeException("No se ha encontrado el departamento"));
        
        modelMapper.map(departamento, departamentoToUpdate);

        return departamentoRepository.save(departamentoToUpdate);

    }

    @Override
    public DepartamentoDTO convertToDTO(Departamento departamento){

        if (departamento == null) {
            throw new IllegalArgumentException("Departamento no puede ser nulo");
        }
        
       return modelMapper.map(departamento, DepartamentoDTO.class);
        
    }


    @Override
    public Departamento convertToEntity(DepartamentoDTO departamentoDTO){

        if(departamentoDTO == null){
            throw new IllegalArgumentException("Departamento no puede ser nulo");
        }
        return modelMapper.map(departamentoDTO, Departamento.class);
    }






    

}

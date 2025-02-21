/**
 * @ Author: Alex_Molerodev
 * @ Email: alejandromolero.developer@gmail.com
 * @ Create Time: 2025-01-24 11:18:58
 * @ Modified time: 2025-01-24 19:45:11
 */

package com.molerodev.clase.demo_spring.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molerodev.clase.demo_spring.dto.ArticuloDTO;
import com.molerodev.clase.demo_spring.entity.Articulo;
import com.molerodev.clase.demo_spring.repository.ArticuloRepository;

@Service
public class ArticuloService implements IService<Articulo,ArticuloDTO>{


    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private ModelMapper modelmapper;

    
    @Override
    public Articulo save(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    @Override
    public Articulo findById(Long id) {
        return articuloRepository.findById(id).orElse(null);
    }

   
    @Override
    public List<Articulo> findAll() {
        return articuloRepository.findAll();
    }

    
    @Override
    public void deleteById(Long id) {
        if(articuloRepository.existsById(id)){
            articuloRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAll() {
        articuloRepository.deleteAll();
    }

    
    @Override
    public Articulo update(Articulo articulo) {
        Articulo articulotoUpdate = articuloRepository
        .findById(articulo.getIdArt())
        .orElseThrow(() -> new RuntimeException("No se ha encontrado el artículo"));
        
        modelmapper.map(articulo, articulotoUpdate);
        
        return articuloRepository.save(articulotoUpdate);

    }


    @Override
    public ArticuloDTO convertToDTO(Articulo articulo){

        if(articulo == null){
            throw new IllegalArgumentException("El artículo no puede ser nulo");
        }
        return modelmapper.map(articulo, ArticuloDTO.class);
    }

    @Override
    public Articulo convertToEntity(ArticuloDTO dto) {

        if(dto == null){
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        }
        return modelmapper.map(dto, Articulo.class);
    }

   

}

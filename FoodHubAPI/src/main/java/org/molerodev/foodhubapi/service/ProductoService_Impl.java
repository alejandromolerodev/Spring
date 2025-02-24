package org.molerodev.foodhubapi.service;

import org.modelmapper.ModelMapper;
import org.molerodev.foodhubapi.dto.ProductoDTO;
import org.molerodev.foodhubapi.entity.Producto;
import org.molerodev.foodhubapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */

@Service
public class ProductoService_Impl implements IService<Producto, ProductoDTO> {

    @Autowired
    private ProductoRepository productoRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductoDTO> getAll() {
        return productoRepository.findAll().stream()
                .map(producto -> convertToDTO(producto))
                .collect(Collectors.toList());
    }

    public ProductoDTO get(Long id) {
        return convertToDTO(productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha podido encontrar el producto")));
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el producto"));

        modelMapper.map(productoDetails, producto);

        return productoRepository.save(producto);
    }


    public void delete(Long id){
        productoRepository.deleteById(id);
    }


    public Producto convertToEntity(ProductoDTO productoDTO) {
        return modelMapper.map(productoDTO, Producto.class);
    }

    public ProductoDTO convertToDTO(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }


}
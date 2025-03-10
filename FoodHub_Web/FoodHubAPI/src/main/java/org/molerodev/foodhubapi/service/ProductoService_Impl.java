package org.molerodev.foodhubapi.service;

import org.modelmapper.ModelMapper;
import org.molerodev.foodhubapi.dto.ProductoDTO;
import org.molerodev.foodhubapi.entity.Categoria;
import org.molerodev.foodhubapi.entity.Producto;
import org.molerodev.foodhubapi.repository.CategoriaRepository;
import org.molerodev.foodhubapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
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

    @Autowired
    private CategoriaRepository categoriaRepository;

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

   // Método para guardar un Producto, asegurando que la Categoria sea persistida correctamente.
   @Override 
   public Producto save(ProductoDTO producto) {

    Producto productoE = convertToEntity(producto);
    // Verificar si la categoría ya existe
    if (productoE.getCategoria() != null) {
        Categoria categoria = productoE.getCategoria();
        
        // Si la categoría no tiene un id asignado (no existe en la base de datos)
        if (categoria.getId() == null) {
            // Guardar la categoría antes de persistir el producto
            categoria = categoriaRepository.save(categoria);
        }
        
        // Asignar la categoría persistida al producto
        productoE.setCategoria(categoria);
    }
    
    // Guardar el producto, la categoría ya estará persistida o actualizada.
    return productoRepository.save(productoE);
}


    public Producto updateProducto(Long id, ProductoDTO productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el producto"));

        modelMapper.map(productoDetails, producto);

        return productoRepository.save(producto);
    }


    public void delete(Long id){
        productoRepository.deleteById(id);
    }




    @Override
    public Producto convertToEntity(ProductoDTO productoDTO) {
        return modelMapper.map(productoDTO, Producto.class);
    }

    @Override
    public ProductoDTO convertToDTO(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    @Override
    public Producto updateFecha(Long id, LocalDate fecha) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

    // Actualizar solo la fecha de caducidad
    producto.setFechaCad(fecha);

    // Guardar los cambios
    return productoRepository.save(producto);
    }

   


}
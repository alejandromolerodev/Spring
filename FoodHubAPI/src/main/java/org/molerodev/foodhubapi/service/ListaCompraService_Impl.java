package org.molerodev.foodhubapi.service;

import org.modelmapper.ModelMapper;
import org.molerodev.foodhubapi.dto.ListaCompraDTO;
import org.molerodev.foodhubapi.entity.Item;
import org.molerodev.foodhubapi.entity.ListaCompra;
import org.molerodev.foodhubapi.model.Estado;
import org.molerodev.foodhubapi.repository.ListaCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 24/2/25
 */

@Service
public class ListaCompraService_Impl implements IService<ListaCompra, ListaCompraDTO> {

    @Autowired
    private ListaCompraRepository listaCompraRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ListaCompraDTO> getAll() {
        return listaCompraRepository.findAll().stream()
                .map(listaCompra -> convertToDTO(listaCompra))
                .collect(Collectors.toList());
    }

    @Override
    public ListaCompraDTO get(Long id) {
        return convertToDTO(listaCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado ninguna lista")));
    }

    @Override
    public ListaCompra save(ListaCompraDTO listaCompraDTO) {
        
        listaCompraDTO.getItems().forEach(item -> item.setEstado(Estado.NO_COMPRADO));
        return listaCompraRepository.save(convertToEntity(listaCompraDTO));
    }

    @Override
    public ListaCompra updateProducto(Long id, ListaCompraDTO listaDetails) {
        ListaCompra lista = listaCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado"));

        // Actualizar campos básicos
        lista.setTitulo(listaDetails.getTitulo());
        lista.setFecha(listaDetails.getFecha());

        // Actualizar cada item individualmente
        if (listaDetails.getItems() != null) {
            for (int i = 0; i < listaDetails.getItems().size(); i++) {
                Item itemDTO =listaDetails.getItems().get(i);
                Item item = lista.getItems().get(i);

                // Actualizar estado solo si viene en el DTO
                if (itemDTO.getEstado() != null) {
                    item.setEstado(itemDTO.getEstado());
                }
            }
        }

        return listaCompraRepository.save(lista);
    }

    @Override
    public void delete(Long id) {

        listaCompraRepository.deleteById(id);

    }

    @Override
    public ListaCompra convertToEntity(ListaCompraDTO listaCompraDTO) {
        return modelMapper.map(listaCompraDTO, ListaCompra.class);
    }

    @Override
    public ListaCompraDTO convertToDTO(ListaCompra listaCompra) {
        return modelMapper.map(listaCompra, ListaCompraDTO.class);
    }

    @Override
    public ListaCompra updateFecha(Long id, LocalDate fecha) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFecha'");
    }
}

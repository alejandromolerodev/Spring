package org.molerodev.foodhubapi.service;

import org.modelmapper.ModelMapper;
import org.molerodev.foodhubapi.dto.ListaCompraDTO;
import org.molerodev.foodhubapi.entity.ListaCompra;
import org.molerodev.foodhubapi.entity.Producto;
import org.molerodev.foodhubapi.repository.ListaCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return listaCompraRepository.save(convertToEntity(listaCompraDTO));
    }

    @Override
    public ListaCompra updateProducto(Long id, ListaCompraDTO listaDetails) {
        ListaCompra lista = listaCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado"));

        modelMapper.map(listaDetails, lista);

        return lista;
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
}

package org.molerodev.foodhubapi.service;

import org.molerodev.foodhubapi.dto.ListaCompraDTO;
import org.molerodev.foodhubapi.entity.ListaCompra;
import org.molerodev.foodhubapi.entity.Producto;

import java.util.List;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 24/2/25
 */
public class ListaCompraService_Impl implements IService<ListaCompra, ListaCompraDTO> {
    @Override
    public List<ListaCompraDTO> getAll() {
        return List.of();
    }

    @Override
    public ListaCompraDTO get(Long id) {
        return null;
    }

    @Override
    public ListaCompra save(ListaCompra listaCompra) {
        return null;
    }

    @Override
    public Producto updateProducto(Long id, Producto productoDetails) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

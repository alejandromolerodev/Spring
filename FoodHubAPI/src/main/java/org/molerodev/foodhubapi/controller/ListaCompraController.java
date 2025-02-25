package org.molerodev.foodhubapi.controller;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 25/2/25
 */

import org.molerodev.foodhubapi.dto.ListaCompraDTO;
import org.molerodev.foodhubapi.entity.ListaCompra;
import org.molerodev.foodhubapi.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foodhub/listacompra")
public class ListaCompraController {

    @Autowired
    private IService<ListaCompra, ListaCompraDTO> iLCService;


    @GetMapping
    public ResponseEntity<List<ListaCompraDTO>> getAll() {
        List<ListaCompraDTO> listaCompras = iLCService.getAll();
        return new ResponseEntity<>(listaCompras, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaCompraDTO> getListaCompra(@PathVariable Long id) {
        ListaCompraDTO listaCompra = iLCService.get(id);
        return new ResponseEntity<>(listaCompra, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ListaCompra> save(@RequestBody ListaCompraDTO listaCompraDTO) {
        ListaCompra savedlista = iLCService.save(listaCompraDTO);
        return new ResponseEntity<>(savedlista, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaCompra> update(@PathVariable Long id, @RequestBody ListaCompraDTO listaCompraDTO) {
        ListaCompra updatedlistaCompra = iLCService.updateProducto(id, listaCompraDTO);
        return new ResponseEntity<>(updatedlistaCompra, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iLCService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

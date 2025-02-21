package org.molerodev.foodhubapi.controller;

import org.molerodev.foodhubapi.dto.ProductoDTO;
import org.molerodev.foodhubapi.entity.Producto;
import org.molerodev.foodhubapi.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Author: Alex_Molerodev
 * Email: alejandromolero.developer@gmail.com
 * Create Time: 18/2/25
 */



@RestController
@RequestMapping("/foodhub")
public class ProductoController {

    @Autowired
    private IService<Producto, ProductoDTO> iService;


    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAll() {
        List<ProductoDTO> productos = iService.getAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        ProductoDTO producto = iService.getProducto(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<String>getImagen(@PathVariable Long id){
        ProductoDTO producto = iService.getProducto(id);

        return new ResponseEntity<>(producto.getUrl_image(),HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        Producto savedProducto = iService.save(producto);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updatedProducto = iService.updateProducto(id, producto);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
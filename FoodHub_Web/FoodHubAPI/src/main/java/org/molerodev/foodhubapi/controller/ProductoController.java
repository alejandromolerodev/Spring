package org.molerodev.foodhubapi.controller;

import org.molerodev.foodhubapi.dto.ProductoDTO;
import org.molerodev.foodhubapi.entity.Producto;
import org.molerodev.foodhubapi.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    private IService<Producto, ProductoDTO> iPService;


    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAll() {
        List<ProductoDTO> productos = iPService.getAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        ProductoDTO producto = iPService.get(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<String>getImagen(@PathVariable Long id){
        ProductoDTO producto = iPService.get(id);

        return new ResponseEntity<>(producto.getUrl_image(),HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody ProductoDTO productoDTO) {
        Producto savedProducto = iPService.save(productoDTO);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody ProductoDTO producto) {
        Producto updatedProducto = iPService.updateProducto(id, producto);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    @PutMapping("/{id}/actualizarFecha")
public ResponseEntity<Producto> updateFechaCaducidad(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate nuevaFecha) {
    Producto updatedProducto = iPService.updateFecha(id, nuevaFecha);
    return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
}

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iPService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
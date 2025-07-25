package com.blanquita.blanquitagestion.controller;

import com.blanquita.blanquitagestion.entity.Producto;
import com.blanquita.blanquitagestion.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) throws Exception {
            Producto productoNuevo = productoService.crearProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping
    public List<Producto> obtenerTodos(){return productoService.getAllProductos();}

    @GetMapping("/{id}")
    public Optional<Producto> getProductoPorId(@PathVariable Long id){return productoService.getProductoById(id);}

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,
                                                       @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<?> baja(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> cambiarStock(@PathVariable Long id) {
        try {
            Producto productoActualizado = productoService.actualizarStock(id);
            return ResponseEntity.ok(productoActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
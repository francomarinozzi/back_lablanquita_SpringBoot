package com.blanquita.blanquitagestion.controller;

import com.blanquita.blanquitagestion.dto.VentaDTO;
import com.blanquita.blanquitagestion.entity.Producto;
import com.blanquita.blanquitagestion.entity.Venta;
import com.blanquita.blanquitagestion.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired

    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody Venta venta) {
        VentaDTO nuevaVenta = ventaService.crearVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @GetMapping
    public List<VentaDTO> obtenerTodos(){return ventaService.getAllVentas();}

    @GetMapping("/{id}")
    public Optional<VentaDTO> getVentaPorId(@PathVariable Long id){return ventaService.getVentaById(id);}

    @PatchMapping("/{id}/baja")
    public ResponseEntity<?> baja(@PathVariable Long id) {
        try {
            ventaService.eliminarVenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}

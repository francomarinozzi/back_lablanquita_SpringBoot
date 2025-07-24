package com.blanquita.blanquitagestion.controller;

import com.blanquita.blanquitagestion.dto.PedidoDTO;
import com.blanquita.blanquitagestion.dto.PedidoRequestDTO;
import com.blanquita.blanquitagestion.entity.Pedido;
import com.blanquita.blanquitagestion.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        PedidoDTO nuevoPedido = pedidoService.crearPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping
    public List<PedidoDTO> obtenerTodos(){return pedidoService.getAllPedidos();}

    @GetMapping("/{id}")
    public Optional<PedidoDTO> getPedidoPorId(@PathVariable Long id){return pedidoService.getPedidoById(id);}


    @PatchMapping("/{id}/baja")
    public ResponseEntity<?> baja(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}


package com.blanquita.blanquitagestion.controller;

import com.blanquita.blanquitagestion.dto.PedidoDTO;
import com.blanquita.blanquitagestion.dto.PedidoRequestDTO;
import com.blanquita.blanquitagestion.entity.Pedido;
import com.blanquita.blanquitagestion.service.PedidoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
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

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id) {
        try {
            PedidoDTO pedidoActualizado = pedidoService.actualizarEstado(id);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //Esta forma de devolver errores es interesante.
    //Puedo usarla en un futuro para evitar tener que desarmar el mensaje de error en el front.
    //Tener en cuenta para un posible refactor
}


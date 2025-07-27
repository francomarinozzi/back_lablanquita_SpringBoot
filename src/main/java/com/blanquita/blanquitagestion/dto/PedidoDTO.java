package com.blanquita.blanquitagestion.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO (
    Long id,
    Long idVenta,
    String direccion,
    String nombreCliente,
    String estado,
    boolean activo,
    LocalDateTime fechaHora,
    List<DetalleVentaDTO> detalles
){}

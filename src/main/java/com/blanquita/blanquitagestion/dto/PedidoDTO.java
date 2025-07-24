package com.blanquita.blanquitagestion.dto;

import java.util.List;

public record PedidoDTO (
    Long id,
    Long idVenta,
    String direccion,
    String nombreCliente,
    String estado,
    boolean activo,
    List<DetalleVentaDTO> detalles
){}

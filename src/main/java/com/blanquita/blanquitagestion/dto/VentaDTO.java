package com.blanquita.blanquitagestion.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record VentaDTO(
        Long id,
        LocalDateTime fechaHora,
        String formaPago,
        BigDecimal total,
        boolean activo,
        List<DetalleVentaDTO> detalles
) {}
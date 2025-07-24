package com.blanquita.blanquitagestion.dto;

import java.math.BigDecimal;

public record DetalleVentaDTO(
        String nombreProducto,
        BigDecimal cantidad,
        BigDecimal precioUnitario
) {}
package com.blanquita.blanquitagestion.dto;

import com.blanquita.blanquitagestion.entity.Venta; // Importa la entidad Venta
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
        @NotBlank String nombreCliente,
        @NotBlank String direccion,
        @NotNull Venta venta // Incluye el objeto Venta completo, tal como lo recibes ahora
) {}
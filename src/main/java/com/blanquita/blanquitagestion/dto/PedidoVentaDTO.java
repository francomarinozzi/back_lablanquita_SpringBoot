package com.blanquita.blanquitagestion.dto;

import com.blanquita.blanquitagestion.entity.Pedido;
import com.blanquita.blanquitagestion.entity.Venta;
import lombok.Data;

@Data
public class PedidoVentaDTO {
    private Pedido pedido;
    private Venta venta;
}

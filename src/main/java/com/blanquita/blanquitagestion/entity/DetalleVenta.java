package com.blanquita.blanquitagestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data

@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @NotNull
    @Column(name="cantidad")
    private BigDecimal cantidad;

    @NotNull
    @Column(name="precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name="subtotal")
    private BigDecimal subtotal;
}


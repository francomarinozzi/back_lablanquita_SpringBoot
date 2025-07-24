package com.blanquita.blanquitagestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;

@Entity

@Data
@Table(name = "Pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @Column(name="direccion", nullable = true) //Los pedidos pueden ser para retirar por mostrador
    private String direccion;

    @Column(name="nombre_cliente", nullable=true) //El nombre de los clientes que realizan pedidos para delivery a veces no se sabe;
    private String nombreCliente;

    @Column(name="estado")
    private String estado; // Puede ser Completado, pendiente o en proceso;

    @Column(name="activo")
    private boolean activo;
}
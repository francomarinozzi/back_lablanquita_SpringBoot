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

    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private Estado estado; // Puede ser Completado, pendiente o en proceso;

    @Column(name="activo")
    private boolean activo;

    @Column(name="comentarios", nullable = true)
    private String comentarios;

    public enum Estado {
        PENDIENTE("Pendiente"),
        EN_PROCESO("En proceso"),
        COMPLETADO("Completado");

        private final String descripcion;

        Estado(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public Estado siguiente() {
            switch (this) {
                case PENDIENTE:
                    return EN_PROCESO;
                case EN_PROCESO:
                    return COMPLETADO;
                case COMPLETADO:
                    throw new IllegalStateException("El pedido ya está completo");
                default:
                    throw new IllegalArgumentException("Estado desconocido");
            }
        }
    }
}
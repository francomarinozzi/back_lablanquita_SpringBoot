package com.blanquita.blanquitagestion.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity

@Data
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre", unique = true)
    private String nombre;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "en_stock")
    private boolean en_stock = true;

    @Column(name = "activo")
    private boolean activo = true;

}
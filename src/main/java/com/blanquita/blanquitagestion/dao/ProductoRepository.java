package com.blanquita.blanquitagestion.dao;
import com.blanquita.blanquitagestion.entity.Producto;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombre(String nombre);

    Optional<Producto> findByNombre(String nombre);
}


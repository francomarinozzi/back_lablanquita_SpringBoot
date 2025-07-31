package com.blanquita.blanquitagestion.service.spec;

import com.blanquita.blanquitagestion.entity.Pedido;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> withFilters(Long id, String nombreCliente, Pedido.Estado estado, LocalDate fecha) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (nombreCliente != null && !nombreCliente.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombreCliente")), "%" + nombreCliente.toLowerCase() + "%"));
            }
            if (estado != null) {
                predicates.add(criteriaBuilder.equal(root.get("estado"), estado));
            }
            if (fecha != null) {

                predicates.add(criteriaBuilder.between(root.get("venta").get("fechaHora"), fecha.atStartOfDay(), fecha.plusDays(1).atStartOfDay()));
            }

            predicates.add(criteriaBuilder.isTrue(root.get("activo")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
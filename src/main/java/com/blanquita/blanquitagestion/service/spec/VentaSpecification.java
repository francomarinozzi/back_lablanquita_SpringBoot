package com.blanquita.blanquitagestion.service.spec;

import com.blanquita.blanquitagestion.entity.Venta;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaSpecification {

    public static Specification<Venta> withFilters(Long id, LocalDate fecha) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }

            if (fecha != null) {
                predicates.add(criteriaBuilder.between(root.get("fechaHora"), fecha.atStartOfDay(), fecha.plusDays(1).atStartOfDay()));
            }

            predicates.add(criteriaBuilder.isTrue(root.get("activo")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
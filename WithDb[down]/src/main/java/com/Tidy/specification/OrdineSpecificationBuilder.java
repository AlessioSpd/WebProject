package com.Tidy.specification;

import com.Tidy.common.SpecificationBuilder;
import com.Tidy.criteria.OrdineCriteria;
import com.Tidy.entity.Ordine;
import com.Tidy.entity.Prenotazione;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrdineSpecificationBuilder extends SpecificationBuilder<Ordine, OrdineCriteria> {
    @Override
    public Specification<Ordine> filter(OrdineCriteria criteria) {

        Specification<Ordine> specification = Specification.where(null);

        if (criteria.getLocaleId() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("target", criteria.getLocaleId()));
        }

        if (criteria.getClienteId() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("source", criteria.getClienteId()));
        }

        if (criteria.getStato() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("stato", criteria.getStato()));
        }

        return specification;
    }
}
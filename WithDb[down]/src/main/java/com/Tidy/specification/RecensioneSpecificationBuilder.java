package com.Tidy.specification;

import com.Tidy.common.SpecificationBuilder;
import com.Tidy.criteria.RecensioneCriteria;
import com.Tidy.entity.Prenotazione;
import com.Tidy.entity.Recensione;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RecensioneSpecificationBuilder extends SpecificationBuilder<Recensione, RecensioneCriteria> {
    @Override
    public Specification<Recensione> filter(RecensioneCriteria criteria) {

        Specification<Recensione> specification = Specification.where(null);

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
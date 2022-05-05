package com.Tidy.specification;

import com.Tidy.common.SpecificationBuilder;
import com.Tidy.criteria.ClienteCriteria;
import com.Tidy.entity.Cliente;
import com.Tidy.entity.Locale;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClienteSpecificationBuilder extends SpecificationBuilder<Cliente, ClienteCriteria> {
    @Override
    public Specification<Cliente> filter(ClienteCriteria criteria) {

        Specification<Cliente> specification = Specification.where(null);

        if (criteria.getToken() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("token", criteria.getToken()));
        }

        if (criteria.getEmail() != null) {
            specification = Objects.requireNonNull(specification).and(likeLowerSpecification( "email", criteria.getEmail()));
        }

        if (criteria.getBlacklist() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("blacklist", criteria.getBlacklist()));
        }

        return specification;
    }
}
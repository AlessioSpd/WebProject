package com.Tidy.specification;

import com.Tidy.common.SpecificationBuilder;
import com.Tidy.criteria.LocaleCriteria;
import com.Tidy.entity.Locale;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LocaleSpecificationBuilder extends SpecificationBuilder<Locale, LocaleCriteria> {
    @Override
    public Specification<Locale> filter(LocaleCriteria criteria) {

        Specification<Locale> specification = Specification.where(null);

        if (criteria.getEmail() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("email", criteria.getEmail()));
        }

        if (criteria.getId() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("id", criteria.getId()));
        }

        if(criteria.getLuogo() != null){
            specification = Objects.requireNonNull(specification).and(likeLowerSpecification("luogo", criteria.getLuogo()));
        }

        if(criteria.getTag() != null){
            specification = Objects.requireNonNull(specification).and(likeLowerSpecification("tag", criteria.getTag()));
        }

        if(criteria.getMedia() != null){
            specification = Objects.requireNonNull(specification).and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("media"), criteria.getMedia()));
        }

        if (criteria.getToken() != null) {
            specification = Objects.requireNonNull(specification).and(equalsSpecification("token", criteria.getToken()));
        }

        return specification;
    }
}
package com.Tidy.specification;

import com.Tidy.common.SpecificationBuilder;
import com.Tidy.criteria.AmministratoreCriteria;
import com.Tidy.entity.Amministratore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AmministratoreSpecificationBuilder extends SpecificationBuilder<Amministratore, AmministratoreCriteria> {
    @Override
    public Specification<Amministratore> filter(AmministratoreCriteria criteria) {
        return null;
    }
}
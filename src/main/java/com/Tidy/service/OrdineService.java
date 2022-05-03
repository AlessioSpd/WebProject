package com.Tidy.service;

import com.Tidy.common.ServiceTemplate;
import com.Tidy.dto.OrdineDto;
import com.Tidy.entity.Ordine;
import com.Tidy.repository.OrdineRepository;
import com.Tidy.criteria.OrdineCriteria;
import com.Tidy.specification.OrdineSpecificationBuilder;
import com.Tidy.mapper.OrdineBidirectionalMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdineService extends ServiceTemplate<Ordine, OrdineDto, OrdineCriteria, OrdineSpecificationBuilder, OrdineBidirectionalMapper, OrdineRepository> {

    protected OrdineService(OrdineRepository repository, OrdineBidirectionalMapper mapper, OrdineSpecificationBuilder specificationBuilder) {
        super(repository, mapper, specificationBuilder);
    }

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public String[] populate(Ordine entity) {
        return new String[0];
    }

    @Override
    protected boolean eligibleToDelete(Long id) {
        return true;
    }

    @Override
    public String getEntityName() {
        return null;
    }

    public List<OrdineDto> getAll() {
        return getRepository().findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
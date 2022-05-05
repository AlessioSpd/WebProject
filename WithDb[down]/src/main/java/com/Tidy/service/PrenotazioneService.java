package com.Tidy.service;

import com.Tidy.common.ServiceTemplate;
import com.Tidy.dto.PrenotazioneDto;
import com.Tidy.entity.Prenotazione;
import com.Tidy.repository.PrenotazioneRepository;
import com.Tidy.criteria.PrenotazioneCriteria;
import com.Tidy.specification.PrenotazioneSpecificationBuilder;
import com.Tidy.mapper.PrenotazioneBidirectionalMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrenotazioneService extends ServiceTemplate<Prenotazione, PrenotazioneDto, PrenotazioneCriteria, PrenotazioneSpecificationBuilder, PrenotazioneBidirectionalMapper, PrenotazioneRepository> {

    protected PrenotazioneService(PrenotazioneRepository repository, PrenotazioneBidirectionalMapper mapper, PrenotazioneSpecificationBuilder specificationBuilder) {
        super(repository, mapper, specificationBuilder);
    }

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public String[] populate(Prenotazione entity) {
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

    public List<PrenotazioneDto> getAll() {
        return getRepository().findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
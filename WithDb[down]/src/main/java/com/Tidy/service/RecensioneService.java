package com.Tidy.service;

import com.Tidy.common.ServiceTemplate;
import com.Tidy.criteria.OrdineCriteria;
import com.Tidy.criteria.PrenotazioneCriteria;
import com.Tidy.dto.OrdineDto;
import com.Tidy.dto.PrenotazioneDto;
import com.Tidy.dto.RecensioneDto;
import com.Tidy.entity.Locale;
import com.Tidy.entity.Recensione;
import com.Tidy.repository.LocaleRepository;
import com.Tidy.repository.RecensioneRepository;
import com.Tidy.criteria.RecensioneCriteria;
import com.Tidy.specification.RecensioneSpecificationBuilder;
import com.Tidy.mapper.RecensioneBidirectionalMapper;
import com.Tidy.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecensioneService extends ServiceTemplate<Recensione, RecensioneDto, RecensioneCriteria, RecensioneSpecificationBuilder, RecensioneBidirectionalMapper, RecensioneRepository> {

    protected RecensioneService(RecensioneRepository repository, RecensioneBidirectionalMapper mapper, RecensioneSpecificationBuilder specificationBuilder) {
        super(repository, mapper, specificationBuilder);
    }

    @Autowired
    PrenotazioneService prenotazioneService;

    @Autowired
    OrdineService ordineService;

    @Autowired
    LocaleRepository localeRepository;

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public String[] populate(Recensione entity) {
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

    public List<RecensioneDto> getAll() {
        return getRepository().findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public RecensioneDto saveRecensione(RecensioneDto dto) {

        Long localeId = dto.getTarget().getId();

        Locale locale = localeRepository.getById(localeId);

        RecensioneCriteria criteria = new RecensioneCriteria();
        criteria.setLocaleId(localeId);
        Page<RecensioneDto> recensionePage = filter(criteria);

        List<RecensioneDto> recensioneDtoList = recensionePage.stream().toList();

        int somma = dto.getRating();
        int length = recensioneDtoList.size() + 1;

        for (RecensioneDto recensione : recensioneDtoList){
            somma = somma + recensione.getRating();
        }

        int media = somma / length;

        locale.setMedia(media);
        localeRepository.save(locale);

        Recensione entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public boolean canAdd(Long userId, Long localeId) {

        LocalDateTime currentData = LocalDateTime.now();
//        currentData = currentData.plusHours(23);
//        currentData = currentData.plusMinutes(59);

        //controllo se l'utente ha gia recensione il locale o se na una in pending
        RecensioneCriteria criteria = new RecensioneCriteria();
        criteria.setLocaleId(localeId);
        criteria.setClienteId(userId);
        List<RecensioneDto> recensioneList = filter(criteria).getContent().stream().toList();
        for (int i = 0; i < recensioneList.size(); i++) {
            RecensioneDto recensione = recensioneList.get(i);
            if(recensione.getStato() == 0 || recensione.getStato() == 1){
                return false;
            }
        }

        //controllo se l'utente ha fatto una prenotazione/ordine conclusa presso il locale
        OrdineCriteria ordineCriteria = new OrdineCriteria();
        ordineCriteria.setStato(1);
        ordineCriteria.setLocaleId(localeId);
        ordineCriteria.setClienteId(userId);
        List<OrdineDto> ordini = ordineService.filter(ordineCriteria).stream().toList();
        for (int i = 0; i < ordini.size(); i++) {
            OrdineDto ordine = ordini.get(i);
            LocalDateTime dataCreazioneOrdine = ordine.getOrario();
            dataCreazioneOrdine = dataCreazioneOrdine.plusDays(1);
            if(currentData.isAfter(dataCreazioneOrdine)){
                return true;
            }
        }

        //controllo se l'utente ha fatto una prenotazione/ordine conclusa presso il locale
        PrenotazioneCriteria prenotazioneCriteria = new PrenotazioneCriteria();
        prenotazioneCriteria.setStato(1);
        prenotazioneCriteria.setLocaleId(localeId);
        prenotazioneCriteria.setClienteId(userId);
        List<PrenotazioneDto> prenotazioni = prenotazioneService.filter(prenotazioneCriteria).stream().toList();
        for (int i = 0; i < prenotazioni.size(); i++) {
            PrenotazioneDto prenotazione = prenotazioni.get(i);
            LocalDateTime dataPrenotazione = prenotazione.getData();
            dataPrenotazione = dataPrenotazione.plusDays(1);
            if(currentData.isAfter(dataPrenotazione)){
                return true;
            }
        }

        return false;
    }
}
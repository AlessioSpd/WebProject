package com.Tidy.util;

import com.Tidy.criteria.PrenotazioneCriteria;
import com.Tidy.dto.PrenotazioneDto;
import com.Tidy.entity.Ordine;
import com.Tidy.entity.Prenotazione;
import com.Tidy.repository.OrdineRepository;
import com.Tidy.repository.PrenotazioneRepository;
import com.Tidy.service.OrdineService;
import com.Tidy.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    @Autowired
    OrdineRepository ordineRepository;

    @Scheduled(fixedRate = 60000)
    public void scheduledTasks(){
        LocalDateTime dataCorrente = LocalDateTime.now();
        aggiornaOrdini(dataCorrente);
        aggiornaPrenotazioni(dataCorrente);
    }

    private void aggiornaPrenotazioni(LocalDateTime dataCorrente){
        List<Prenotazione> prenotazioniPending = prenotazioneRepository.findByStato(0);
        for (int i = 0; i < prenotazioniPending.size(); i++) {
            Prenotazione prenotazione = prenotazioniPending.get(i);
            LocalDateTime dataPrenotazione = prenotazione.getData();
            dataPrenotazione = dataPrenotazione.minusHours(1);
            if(dataCorrente.isAfter(dataPrenotazione)){
                prenotazione.setStato(-1);
                prenotazioneRepository.save(prenotazione);
            }
        }
    }

    private void aggiornaOrdini(LocalDateTime dataCorrente) {
        List<Ordine> ordiniPending = ordineRepository.findByStato(0);
        for (int i = 0; i < ordiniPending.size(); i++) {
            Ordine ordine = ordiniPending.get(i);
            LocalDateTime dataOrdine = ordine.getOrario();
            dataOrdine = dataOrdine.minusHours(1);
            if(dataCorrente.isAfter(dataOrdine)){
                ordine.setStato(-1);
                ordineRepository.save(ordine);
            }
        }
    }

}

package com.Tidy.repository;

import com.Tidy.common.BaseJpaRepository;
import com.Tidy.entity.Prenotazione;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends BaseJpaRepository<Prenotazione> {
    public List<Prenotazione> findByStato(Integer stato);
}
package com.Tidy.repository;

import com.Tidy.common.BaseJpaRepository;
import com.Tidy.entity.Ordine;
import com.Tidy.entity.Prenotazione;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdineRepository extends BaseJpaRepository<Ordine> {
    public List<Ordine> findByStato(Integer stato);
}
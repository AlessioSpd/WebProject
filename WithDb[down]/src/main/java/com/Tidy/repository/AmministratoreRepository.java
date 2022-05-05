package com.Tidy.repository;

import com.Tidy.common.BaseJpaRepository;
import com.Tidy.entity.Amministratore;
import com.Tidy.entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface AmministratoreRepository extends BaseJpaRepository<Amministratore> {
    Amministratore findByEmail(String email);
}
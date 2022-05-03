package com.Tidy.repository;

import com.Tidy.common.BaseJpaRepository;
import com.Tidy.entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseJpaRepository<Cliente> {
    Cliente findByEmail(String email);
}
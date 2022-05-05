package com.Tidy.repository;

import com.Tidy.common.BaseJpaRepository;
import com.Tidy.entity.Locale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocaleRepository extends BaseJpaRepository<Locale> {
    Locale findByEmail(String email);
}
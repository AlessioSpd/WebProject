package com.Tidy.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<E extends com.Tidy.common.BaseEntity> extends JpaSpecificationExecutor<E>, JpaRepository<E, Long> {
}

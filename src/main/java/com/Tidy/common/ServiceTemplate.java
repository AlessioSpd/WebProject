package com.Tidy.common;

import com.Tidy.exception.BaseCustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import static com.Tidy.util.Constants.deleteRestrictionError;
import static com.Tidy.util.Constants.notFoundError;

@Transactional
public abstract class ServiceTemplate<
        E extends com.Tidy.common.BaseEntity,
        D extends com.Tidy.common.BaseDto,
        C extends com.Tidy.common.BaseCriteria,
        S extends com.Tidy.common.SpecificationBuilder<E, C>,
        M extends com.Tidy.common.BidirectionalMapper<D, E>,
        R extends com.Tidy.common.BaseJpaRepository<E>> {
    protected final R repository;
    protected final M mapper;
    protected final S specificationBuilder;

    protected ServiceTemplate(
            @NonNull R repository,
            @NonNull M mapper,
            @NonNull S specificationBuilder) {
        this.repository = repository;
        this.mapper = mapper;
        this.specificationBuilder = specificationBuilder;
    }

    public R getRepository() {
        return repository;
    }

    public M getMapper() {
        return mapper;
    }

    public Page<D> filter(@Nullable C criteria) {
        return getEntities(criteria).map(mapper::toDto);
    }

    protected Page<E> getEntities(@Nullable C criteria) {
        Pageable pageable = PageRequest.of(0,100);
        if (criteria != null){
            pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                    Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        }

        return (criteria != null)
                ? repository.findAll(specificationBuilder.filter(criteria), pageable)
                : repository.findAll(pageable);

    }

    public abstract String[] getHeaders();

    public abstract String[] populate(E entity);

    public com.Tidy.common.BaseDto save(@NotNull D dto) throws SQLDataException {
        E entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public D update(@NotNull D dto, Long id) throws BaseCustomException {
        E entityFromDb = getEntity(id);
        E updatedEntity = mapper.toUpdateEntity(dto, entityFromDb);
        E savedEntity = repository.save(updatedEntity);
        return mapper.toDto(savedEntity);
    }

    public D delete(Long id) throws BaseCustomException {
        if (!eligibleToDelete(id)) {
            throw new BaseCustomException(deleteRestrictionError, HttpStatus.NOT_FOUND);
        }
        E entity = getEntity(id);
        entity.deleted = 1L;
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public E getEntity(Long id) throws BaseCustomException {
        return repository.findById(id)
                .orElseThrow(() -> new BaseCustomException(notFoundError, HttpStatus.NOT_FOUND));
    }

    public D getDto(Long id) throws BaseCustomException {
        return mapper.toDto(getEntity(id));
    }

    protected abstract boolean eligibleToDelete(Long id);

    public abstract String getEntityName();

}

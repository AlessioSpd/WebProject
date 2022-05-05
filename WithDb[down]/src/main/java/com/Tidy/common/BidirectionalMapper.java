package com.Tidy.common;

public interface BidirectionalMapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);
    
    E toUpdateEntity(D dto, E entity);
}

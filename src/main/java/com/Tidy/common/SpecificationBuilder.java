package com.Tidy.common;

import org.springframework.data.jpa.domain.Specification;

public abstract class SpecificationBuilder<E extends com.Tidy.common.BaseEntity, C extends com.Tidy.common.BaseCriteria> {

    public abstract Specification<E> filter(C criteria);

    public Specification<E> buildSpecification() {
        return Specification.where(null);
    }


    protected <T> Specification<E> equalsSpecification(String fieldName, T value) {
        return (root, query, builder) -> builder.equal(root.get(fieldName), value);
    }
    protected <T> Specification<E> notEqualsSpecification(String fieldName, T value) {
        return (root, query, builder) -> builder.notEqual(root.get(fieldName), value);
    }

    protected <T> Specification<E> equalsSpecification(Long fieldName, T value) {
        return (root, query, builder) -> builder.equal(root.get(String.valueOf(fieldName)), value);
    }

    protected Specification<E> likeUpperSpecification(String fieldName, String value) {
        return (root, query, builder) ->
                builder.like(builder.upper(root.get(fieldName)), wrapLikeQuery(value));
    }

    protected Specification<E> likeLowerSpecification(String fieldName, String value) {
        if(!value.equals("")){
            return (root, query, builder) ->
                    builder.like(builder.lower(root.get(fieldName)), wrapLikeQuery(value));
        }
        return null;
    }

    protected static String wrapLikeQuery(String txt) {
        return "%" + txt.toLowerCase() + "%";
    }


}

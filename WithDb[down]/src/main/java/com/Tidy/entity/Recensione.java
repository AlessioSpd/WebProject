package com.Tidy.entity;

import com.Tidy.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicInsert
@Where(clause = "deleted = 0")
public class Recensione extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recensione_seq")
    @SequenceGenerator(sequenceName = "recensione_seq", allocationSize = 1, name = "recensione_seq")
    @Column(columnDefinition = "integer default nextval('recensione_seq')")
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cliente source;

    @ManyToOne(fetch = FetchType.EAGER)
    private Locale target;

    @Column(nullable = false)
    Integer rating;

    @Column(nullable = false)
    String testo;

    @ColumnDefault("0")
    Integer stato;
}
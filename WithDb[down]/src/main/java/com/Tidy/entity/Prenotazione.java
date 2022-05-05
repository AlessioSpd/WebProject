package com.Tidy.entity;

import com.Tidy.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicInsert
@Where(clause = "deleted = 0")
public class Prenotazione extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prenotazione_seq")
    @SequenceGenerator(sequenceName = "prenotazione_seq", allocationSize = 1, name = "prenotazione_seq")
    @Column(columnDefinition = "integer default nextval('prenotazione_seq')")
    Long id;

    @Column(nullable = false)
    Integer numeroPersone;

    @Column(nullable = false)
    LocalDateTime data;

    @ManyToOne(fetch = FetchType.EAGER)
    Cliente source;

    @ManyToOne(fetch = FetchType.EAGER)
    Locale target;

    @ColumnDefault("0")
    Integer stato;
}
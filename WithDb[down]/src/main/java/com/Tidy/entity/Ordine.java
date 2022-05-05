package com.Tidy.entity;

import com.Tidy.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicInsert
@Where(clause = "deleted = 0")
public class Ordine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordine_seq")
    @SequenceGenerator(sequenceName = "ordine_seq", allocationSize = 1, name = "ordine_seq")
    @Column(columnDefinition = "integer default nextval('ordine_seq')")
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Cliente source;

    @ManyToOne(fetch = FetchType.EAGER)
    Locale target;

    @Column(nullable = false)
    LocalDateTime orario;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ColumnDefault("'{}'")
    String dettagli;

    @ColumnDefault("0")
    Integer stato;

    @ColumnDefault("''")
    String info;

}
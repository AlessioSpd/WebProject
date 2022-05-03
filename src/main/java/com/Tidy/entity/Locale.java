package com.Tidy.entity;

import com.Tidy.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicInsert
public class Locale extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locale_seq")
    @SequenceGenerator(sequenceName = "locale_seq", allocationSize = 1, name = "locale_seq")
    @Column(columnDefinition = "integer default nextval('locale_seq')")
    Long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String nome;

    @Column(nullable = false)
    String luogo;

    @Column(nullable = false)
    String telefono;

    @ColumnDefault("false")
    Boolean parcheggio;

    @ColumnDefault("true")
    Boolean pos;

    @ColumnDefault("false")
    Boolean accettaOrdini;

    @ColumnDefault("true")
    Boolean accettaRecensioni;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ColumnDefault("'{}'")
    String menu;

    @ColumnDefault("''")
    String tag;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ColumnDefault("'{}'")
    String foto;

    @ColumnDefault("''")
    String descrizione;

    @ColumnDefault("5")
    Integer media;

    @Column(nullable = false)
    String token;

    @ColumnDefault("false")
    Boolean verify;
}
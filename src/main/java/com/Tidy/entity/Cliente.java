package com.Tidy.entity;

import com.Tidy.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicInsert
public class Cliente extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(sequenceName = "cliente_seq", allocationSize = 1, name = "cliente_seq")
    @Column(columnDefinition = "integer default nextval('cliente_seq')")
    Long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String nome;

    @Column(nullable = false)
    String cognome;

    @Column(nullable = false)
    String telefono;

    @ColumnDefault("false")
    Boolean blacklist;

    @Column(nullable = false)
    String token;

    @ColumnDefault("false")
    Boolean verify;
}
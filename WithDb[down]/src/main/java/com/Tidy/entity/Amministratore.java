package com.Tidy.entity;

import com.Tidy.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Amministratore extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amministratore_seq")
    @SequenceGenerator(sequenceName = "amministratore_seq", allocationSize = 1, name = "amministratore_seq")
    @Column(columnDefinition = "integer default nextval('amministratore_seq')")
    Long id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

}
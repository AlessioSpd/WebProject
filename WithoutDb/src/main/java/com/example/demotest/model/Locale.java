package com.example.demotest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Locale {
    private int id;
    private String email;
//    private String password;
    private String nome;
//    private String luogo;
//    private String telefono;
//    private Boolean parcheggio;
//    private Boolean pos;
//    private Boolean accettaOrdini;
    private Boolean accettaRecensioni;
//    private String menu;
    private String tag;
//    private String foto;
//    private String descrizione;
//    private Integer media;
//    private String token;
    private Boolean verify;

    public Locale(int id, String email, String nome, Boolean accettaRecensioni, String tag, Boolean verify) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.accettaRecensioni = accettaRecensioni;
        this.tag = tag;
        this.verify = verify;
    }
}

package com.example.demotest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Utente {
    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String telefono;
    private Boolean blacklist;
    private Boolean verify;

    public Utente(int id, String email, String password, String nome, String cognome, String telefono, boolean blacklist,Boolean verify){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.blacklist = blacklist;
        this.verify = verify;
    }

    public void stampa(){
        System.out.println("id: " + this.id + "\nemail: " + this.email + "\nblacklist: " + this.blacklist);
    }
}
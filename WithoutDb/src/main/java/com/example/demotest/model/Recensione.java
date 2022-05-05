package com.example.demotest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recensione {
    private int id;
    private Utente source;
    private Locale target;
    private Integer rating;
    private String testo;
    private Integer stato;

    public Recensione(int id, Utente source, Locale target, Integer rating, String testo, Integer stato) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.rating = rating;
        this.testo = testo;
        this.stato = stato;
    }
}

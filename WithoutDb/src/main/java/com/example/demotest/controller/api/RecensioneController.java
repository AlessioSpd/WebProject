package com.example.demotest.controller.api;

import com.example.demotest.filter.BlackListFilter;
import com.example.demotest.filter.ReviewFilter;
import com.example.demotest.model.Locale;
import com.example.demotest.model.Recensione;
import com.example.demotest.model.Utente;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RecensioneController {
    private ArrayList<Recensione> recensioni;

    public RecensioneController(){
        this.recensioni = new ArrayList<Recensione>();

        recensioni.add(new Recensione(
                recensioni.size(),
                new Utente(2, "utente@1", "123", "Utente1", "UtenteCogn", "12345678910", true, true),
                new Locale(0, "locale@1", "la locanda", true, "pizzeria", true),
                5,
                "Recensione1",
                1));

        recensioni.add(new Recensione(
                recensioni.size(),
                new Utente(0, "utente@2", "123", "Utente2", "UtenteCogn", "12345678910", true, true),
                new Locale(1, "locale@2", "la caraffa", true, "birreria", true),
                2,
                "Recensione2",
                0));

        recensioni.add(new Recensione(
                recensioni.size(),
                new Utente(0, "utente@3", "123", "Utente3", "UtenteCogn", "12345678910", true, true),
                new Locale(1, "locale@3", "la bottiglia", true, "vineria", true),
                2,
                "Recensione3",
                1));

        recensioni.add(new Recensione(
                recensioni.size(),
                new Utente(0, "utente@4", "123", "Utente4", "UtenteCogn", "12345678910", true, true),
                new Locale(1, "locale@3", "la bottiglia", true, "vineria", true),
                2,
                "Recensione4",
                -1));
    }

    @PostMapping("/admin/manageReview/filter")
    public ArrayList<Recensione> filter(@RequestBody ReviewFilter filter){

        ArrayList<Recensione> temp = new ArrayList<Recensione>();

        for(Recensione rev : recensioni){
            if (rev.getStato() == filter.getStato()){
                temp.add(rev);
            }
        }

        return temp;
    }

    @GetMapping("/admin/manageReview/get/{id}")
    public Recensione getReview(@PathVariable int id){
        for(Recensione ut : recensioni)
            if(ut.getId() == id)
                return ut;
        return null;
    }

    @PutMapping("/admin/manageReview/update/{id}")
    public Boolean update(@PathVariable int id, @RequestBody Recensione review){

        for(int i = 0; i < recensioni.size(); ++i)
            if(recensioni.get(i).getId() == review.getId()){
                recensioni.set(i, review);
                return true;
            }

        return false;
    }
}

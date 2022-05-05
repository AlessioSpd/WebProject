package com.example.demotest.controller.api;
import com.example.demotest.filter.BlackListFilter;
import com.example.demotest.model.Utente;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UtenteController {
    private ArrayList<Utente> utenti;

    public UtenteController(){
        this.utenti = new ArrayList<Utente>();
        this.utenti.add(new Utente(utenti.size(), "utente@1", "123", "Utente1", "UtenteCogn", "12345678910", true, true));
        this.utenti.add(new Utente(utenti.size(), "utente@2", "123", "Utente2", "UtenteCogn", "12345678910", true, true));
        this.utenti.add(new Utente(utenti.size(), "utente@3", "123", "Utente3", "UtenteCogn", "12345678910", true, true));
        this.utenti.add(new Utente(utenti.size(), "utente@4", "123", "Utente4", "UtenteCogn", "12345678910", false, true));
        this.utenti.add(new Utente(utenti.size(), "utente@5", "123", "Utente5", "UtenteCogn", "12345678910", false, true));
        this.utenti.add(new Utente(utenti.size(), "pamuimc@gmail.com", "123", "Utente6", "UtenteCogn", "12345678910", false, true));
    }

    @PostMapping("/admin/manageUser/filter")
    public ArrayList<Utente> filter(@RequestBody BlackListFilter filter){
        ArrayList<Utente> temp = new ArrayList<>();
        if(filter.getEmail().isEmpty()) {
            for(Utente ut : utenti)
                if(ut.getBlacklist() == filter.getBlacklist())
                    temp.add(ut);
        } else {
            for(Utente ut : utenti)
                if(ut.getBlacklist() == filter.getBlacklist() && ut.getEmail().contains(filter.getEmail()))
                    temp.add(ut);
        }

        return temp;
    }

    @GetMapping("/admin/manageUser/get/{mail}")
    public Utente getUtente(@PathVariable String mail){
        for(Utente ut : utenti)
            if(ut.getEmail().equals(mail))
                return ut;

        return null;
    }

    @PutMapping("/admin/manageUser/update/{id}")
    public Boolean update(@PathVariable int id, @RequestBody Utente utente){

        utente.stampa();

        for(int i = 0; i < utenti.size(); ++i)
            if(utenti.get(i).getId() == utente.getId()){
                utenti.set(i, utente);
                System.out.println("trovato ed esco");
                return true;
            }
        System.out.println("non trovato ed esco");
        return false;
    }
}

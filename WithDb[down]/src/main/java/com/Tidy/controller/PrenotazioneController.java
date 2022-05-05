package com.Tidy.controller;

import com.Tidy.common.ControllerTemplate;
import com.Tidy.dto.PrenotazioneDto;
import com.Tidy.criteria.PrenotazioneCriteria;
import com.Tidy.service.PrenotazioneService;
import com.Tidy.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrenotazioneController extends ControllerTemplate<PrenotazioneDto, PrenotazioneCriteria, PrenotazioneService> {
    public PrenotazioneController(PrenotazioneService service) {
        super(service);
    }

    @Autowired
    PrenotazioneService service;

    @PostMapping("public/prenotazione/filter")
    public ResponseEntity<ResponseWrapper> filter(@Nullable @RequestBody PrenotazioneCriteria criteria) {
        return ResponseWrapper.format("filter", () -> service.filter(criteria));
    }

    @PostMapping("public/prenotazione/save")
    public ResponseEntity<ResponseWrapper> save(@NotNull @RequestBody PrenotazioneDto dto) throws SQLDataException {
        return ResponseWrapper.format("save", () -> service.save(dto));
    }

    @GetMapping("public/prenotazione/get/{id}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable Long id) {
        return ResponseWrapper.format("find by id", () -> service.getDto(id));
    }

    @PostMapping("public/prenotazione/update/{id}")
    public ResponseEntity<ResponseWrapper> update(@NotNull @RequestBody PrenotazioneDto dto, @PathVariable Long id) {
        return ResponseWrapper.format("update", () -> service.update(dto, id));
    }

    @DeleteMapping("public/prenotazione/delete/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable Long id) {
        return ResponseWrapper.format("delete", () -> service.delete(id));
    }

}
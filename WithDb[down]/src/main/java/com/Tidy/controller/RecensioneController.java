package com.Tidy.controller;

import com.Tidy.common.ControllerTemplate;
import com.Tidy.dto.RecensioneDto;
import com.Tidy.criteria.RecensioneCriteria;
import com.Tidy.service.RecensioneService;
import com.Tidy.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecensioneController extends ControllerTemplate<RecensioneDto, RecensioneCriteria, RecensioneService> {
    public RecensioneController(RecensioneService service) {
        super(service);
    }

    @Autowired
    RecensioneService service;

    @PostMapping("public/recensione/filter")
    public ResponseEntity<ResponseWrapper> filter(@Nullable @RequestBody RecensioneCriteria criteria) {
        return ResponseWrapper.format("filter", () -> service.filter(criteria));
    }

    @PostMapping("public/recensione/save")
    public ResponseEntity<ResponseWrapper> save(@NotNull @RequestBody RecensioneDto dto) throws SQLDataException {
        return ResponseWrapper.format("save", () -> service.saveRecensione(dto));
    }

    @GetMapping("public/recensione/get/{id}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable Long id) {
        return ResponseWrapper.format("find by id", () -> service.getDto(id));
    }

    @PostMapping("public/recensione/update/{id}")
    public ResponseEntity<ResponseWrapper> update(@NotNull @RequestBody RecensioneDto dto, @PathVariable Long id) {
        return ResponseWrapper.format("update", () -> service.update(dto, id));
    }

    @DeleteMapping("public/recensione/delete/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable Long id) {
        return ResponseWrapper.format("delete", () -> service.delete(id));
    }

    @GetMapping("public/recensione/canAdd/user/{userId}/locale/{localeId}")
    public ResponseEntity<ResponseWrapper> canAdd(@PathVariable Long userId, @PathVariable Long localeId) {
        return ResponseWrapper.format("can add recensione", () -> service.canAdd(userId, localeId));
    }

}
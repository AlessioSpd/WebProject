package com.Tidy.controller;

import com.Tidy.common.ControllerTemplate;
import com.Tidy.dto.OrdineDto;
import com.Tidy.criteria.OrdineCriteria;
import com.Tidy.service.OrdineService;
import com.Tidy.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdineController extends ControllerTemplate<OrdineDto, OrdineCriteria, OrdineService> {
    public OrdineController(OrdineService service) {
        super(service);
    }

    @Autowired
    OrdineService service;

    @PostMapping("public/ordine/filter")
    public ResponseEntity<ResponseWrapper> filter(@Nullable @RequestBody OrdineCriteria criteria) {
        return ResponseWrapper.format("filter", () -> service.filter(criteria));
    }

    @PostMapping("public/ordine/save")
    public ResponseEntity<ResponseWrapper> save(@NotNull @RequestBody OrdineDto dto) throws SQLDataException {
        return ResponseWrapper.format("save", () -> service.save(dto));
    }

    @GetMapping("public/ordine/get/{id}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable Long id) {
        return ResponseWrapper.format("find by id", () -> service.getDto(id));
    }

    @PostMapping("public/ordine/update/{id}")
    public ResponseEntity<ResponseWrapper> update(@NotNull @RequestBody OrdineDto dto, @PathVariable Long id) {
        return ResponseWrapper.format("update", () -> service.update(dto, id));
    }

    @DeleteMapping("public/ordine/delete/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable Long id) {
        return ResponseWrapper.format("delete", () -> service.delete(id));
    }
}
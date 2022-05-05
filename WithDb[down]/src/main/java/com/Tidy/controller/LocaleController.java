package com.Tidy.controller;

import com.Tidy.common.ControllerTemplate;
import com.Tidy.dto.LocaleDto;
import com.Tidy.criteria.LocaleCriteria;
import com.Tidy.request.ChangePasswordRequest;
import com.Tidy.request.LoginRequest;
import com.Tidy.service.LocaleService;
import com.Tidy.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocaleController extends ControllerTemplate<LocaleDto, LocaleCriteria, LocaleService> {
    public LocaleController(LocaleService service) {
        super(service);
    }

    @Autowired
    LocaleService service;

    @PostMapping("public/locale/filter")
    public ResponseEntity<ResponseWrapper> filter(@Nullable @RequestBody LocaleCriteria criteria) {
        return ResponseWrapper.format("filter", () -> service.filter(criteria));
    }

    @PostMapping("public/locale/save")
    public ResponseEntity<ResponseWrapper> save(@NotNull @RequestBody LocaleDto dto) throws SQLDataException {
        return ResponseWrapper.format("save", () -> service.saveLocale(dto));
    }

    /*
    @GetMapping("public/locale/get/{id}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable Long id) {
        return ResponseWrapper.format("find by id", () -> service.getDto(id));
    }
    */

    @GetMapping("public/locale/exists/{email}")
    public ResponseEntity<ResponseWrapper> exists(@PathVariable String email) {
        return ResponseWrapper.format("exists", () -> service.exists(email));
    }

    @GetMapping("public/locale/get/{email}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable String email) {
        return ResponseWrapper.format("find by email", () -> service.getByEmail(email));
    }

    @PostMapping("public/locale/update/{id}")
    public ResponseEntity<ResponseWrapper> update(@NotNull @RequestBody LocaleDto dto, @PathVariable Long id) {
        return ResponseWrapper.format("update", () -> service.update(dto, id));
    }

    @DeleteMapping("public/locale/delete/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable Long id) {
        return ResponseWrapper.format("delete", () -> service.delete(id));
    }

    @PostMapping("public/locale/login")
    public ResponseEntity<ResponseWrapper> login(@NotNull @RequestBody LoginRequest request) {
        return ResponseWrapper.format("login", () -> service.login(request));
    }

    @PostMapping("public/locale/changePassword/{id}")
    public ResponseEntity<ResponseWrapper> update(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseWrapper.format("update", () -> service.changePassword(id, changePasswordRequest));
    }
}
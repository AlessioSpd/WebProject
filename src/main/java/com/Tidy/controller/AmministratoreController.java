package com.Tidy.controller;

import com.Tidy.common.ControllerTemplate;
import com.Tidy.dto.AmministratoreDto;
import com.Tidy.criteria.AmministratoreCriteria;
import com.Tidy.request.ChangePasswordRequest;
import com.Tidy.request.LoginRequest;
import com.Tidy.service.AmministratoreService;
import com.Tidy.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AmministratoreController extends ControllerTemplate<AmministratoreDto, AmministratoreCriteria, AmministratoreService> {
    public AmministratoreController(AmministratoreService service) {
        super(service);
    }

    @Autowired
    AmministratoreService service;

    @PostMapping("public/amministratore/filter")
    public ResponseEntity<ResponseWrapper> filter(@Nullable @RequestBody AmministratoreCriteria criteria) {
        return ResponseWrapper.format("filter", () -> service.filter(criteria));
    }

    @PostMapping("public/amministratore/save")
    public ResponseEntity<ResponseWrapper> save(@NotNull @RequestBody AmministratoreDto dto) throws SQLDataException {
        return ResponseWrapper.format("save", () -> service.saveAmministratore(dto));
    }

    @GetMapping("public/amministratore/get/{email}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable String email) {
        return ResponseWrapper.format("find by email", () -> service.getByEmail(email));
    }

    @PostMapping("public/amministratore/update/{id}")
    public ResponseEntity<ResponseWrapper> update(@NotNull @RequestBody AmministratoreDto dto, @PathVariable Long id) {
        return ResponseWrapper.format("update", () -> service.update(dto, id));
    }

    @DeleteMapping("public/amministratore/delete/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable Long id) {
        return ResponseWrapper.format("delete", () -> service.delete(id));
    }

    @DeleteMapping("public/amministratore/deleteRow/{id}")
    public ResponseEntity<ResponseWrapper> deleteRow(@PathVariable Long id) {
        return ResponseWrapper.format("deleteRow", () -> service.permaDelete(id));
    }

    @GetMapping("public/amministratore/exists/{email}")
    public ResponseEntity<ResponseWrapper> exists(@PathVariable String email) {
        return ResponseWrapper.format("exists", () -> service.exists(email));
    }

    @PostMapping("public/amministratore/login")
    public ResponseEntity<ResponseWrapper> login(@NotNull @RequestBody LoginRequest request) {
        return ResponseWrapper.format("login", () -> service.login(request));
    }

    @PostMapping("public/amministratore/changePassword/{id}")
    public ResponseEntity<ResponseWrapper> update(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseWrapper.format("update", () -> service.changePassword(id, changePasswordRequest));
    }
}
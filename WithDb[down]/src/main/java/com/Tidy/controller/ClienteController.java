package com.Tidy.controller;

import com.Tidy.common.ControllerTemplate;
import com.Tidy.dto.ClienteDto;
import com.Tidy.criteria.ClienteCriteria;
import com.Tidy.request.ChangePasswordRequest;
import com.Tidy.request.LoginRequest;
import com.Tidy.service.ClienteService;
import com.Tidy.util.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController extends ControllerTemplate<ClienteDto, ClienteCriteria, ClienteService> {
    public ClienteController(ClienteService service) {
        super(service);
    }

    @Autowired
    ClienteService service;

    @PostMapping("public/cliente/filter")
    public ResponseEntity<ResponseWrapper> filter(@Nullable @RequestBody ClienteCriteria criteria) {
        return ResponseWrapper.format("filter", () -> service.filter(criteria));
    }

    @PostMapping("public/cliente/save")
    public ResponseEntity<ResponseWrapper> save(@NotNull @RequestBody ClienteDto dto) throws SQLDataException {
        return ResponseWrapper.format("save", () -> service.saveCliente(dto));
    }

    /*
    @GetMapping("public/cliente/get/{id}")
    public ResponseEntity<ResponseWrapper> findById(@PathVariable Long id) {
        return ResponseWrapper.format("find by id", () -> service.getDto(id));
    }
    */

    @GetMapping("public/cliente/exists/{email}")
    public ResponseEntity<ResponseWrapper> exists(@PathVariable String email) {
        return ResponseWrapper.format("exists", () -> service.exists(email));
    }

    @PostMapping("public/cliente/login")
    public ResponseEntity<ResponseWrapper> login(@NotNull @RequestBody LoginRequest request) {
        return ResponseWrapper.format("login", () -> service.login(request));
    }

    @GetMapping("public/cliente/get/{email}")
    public ResponseEntity<ResponseWrapper> findByEmail(@PathVariable String email) {
        return ResponseWrapper.format("find by email", () -> service.getByEmail(email));
    }

    @PostMapping("public/cliente/update/{id}")
    public ResponseEntity<ResponseWrapper> update(@NotNull @RequestBody ClienteDto dto, @PathVariable Long id) {
        return ResponseWrapper.format("update", () -> service.update(dto, id));
    }

    @DeleteMapping("public/cliente/delete/{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable Long id) {
        return ResponseWrapper.format("delete", () -> service.delete(id));
    }

    @PostMapping("public/cliente/changePassword/{id}")
    public ResponseEntity<ResponseWrapper> update(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseWrapper.format("update", () -> service.changePassword(id, changePasswordRequest));
    }
}
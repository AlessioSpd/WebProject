package com.example.demotest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminModel {
    private int id;
    private String email;
    private String password;

    public AdminModel() {
        this.id = 0;
        this.email = "admin@admin";
        this.password = "admin";
    }
}

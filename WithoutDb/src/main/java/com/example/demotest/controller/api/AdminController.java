package com.example.demotest.controller.api;

import com.example.demotest.model.AdminModel;
import com.example.demotest.request.ChangePasswordRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
public class AdminController {
    private AdminModel admin;

    public AdminController(){
        this.admin = new AdminModel();
    }

    @GetMapping("/admin/exists/{mail}")
    public boolean checkMail(@PathVariable String mail){
        return this.admin.getEmail().equals(mail);
    }

    @GetMapping("/admin/check/{pass}")
    public boolean checkPass(@PathVariable String pass){
        System.out.println(pass);
        return this.admin.getPassword().equals(pass);
    }

    @PostMapping("/admin/login")
    public AdminModel checkMail(@RequestBody AdminModel admin){
        System.out.println(admin.getEmail());
        System.out.println(admin.getPassword());
        if (!this.admin.getEmail().equals(admin.getEmail())
                || !this.admin.getPassword().equals(admin.getPassword())) {
            return null;
        } else {
            return admin;
        }
    }

    @PutMapping("/admin/changeMail/{id}")
    public AdminModel checkMail(@PathVariable int id, @RequestBody AdminModel admin){
        if (this.admin.getId() == id){
            this.admin.setEmail(admin.getEmail());
            return this.admin;
        } else {
            return null;
        }
    }

    @PutMapping("/amdin/changePassword/{id}")
    public AdminModel changePassword(@PathVariable int id, @RequestBody ChangePasswordRequest req){
        System.out.println(req.getNewPassword());
        if (this.admin.getId() == id){
            this.admin.setPassword(req.getNewPassword());
            return this.admin;
        } else {
            return null;
        }
    }
}

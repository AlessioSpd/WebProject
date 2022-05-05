package com.example.demotest.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    String oldPassword;
    String newPassword;
}

package com.Tidy.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Auth {

    // CRIPTO LA PASSWORD CON SPRING SECURITY
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // CONTROLLO CHE LA PASSWORD ORIGINALE E QUELLA CRIPTATA
    // PASSINO IL MATCH
    public static boolean checkPassword(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }
}

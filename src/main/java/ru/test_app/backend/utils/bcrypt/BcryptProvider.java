package ru.test_app.backend.utils.bcrypt;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;

public class BcryptProvider {
    public static byte[] hashing(String password) {
        return BCrypt.withDefaults().hash(6, password.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean verify(String password, byte[] hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hash);
        return result.verified;
    }
}

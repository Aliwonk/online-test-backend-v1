package ru.test_app.backend.controllers.auth.controller.DTO;

import ru.test_app.backend.utils.bcrypt.BcryptProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CreateUserDTO(String firstName, String lastName, String email, String password) {

    /* VALIDATION DATA */

    public boolean checkFullName() {
        return !this.firstName.isEmpty() && !this.lastName.isEmpty();
    }

    public boolean checkValidEmail() {
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(this.email);
        return matcher.matches();
    }

    public boolean checkValidPassword() {
        return this.password != null && this.password.length() >= 6;
    }

    public byte[] getHashPassword() {
        return BcryptProvider.hashing(this.password);
    }
}

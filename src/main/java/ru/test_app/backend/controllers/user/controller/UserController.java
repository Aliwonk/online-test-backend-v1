package ru.test_app.backend.controllers.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.test_app.backend.controllers.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UUID getUser(@PathVariable UUID id) {
        System.out.printf("User id: %s \n", id);
        this.userService.getProfile(id);
        return UUID.fromString(id.toString());
    }

    @PatchMapping()
    public String patchUser() {
        return this.userService.updateUser();
    }
}

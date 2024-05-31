package ru.test_app.backend.controllers.auth.service;

import java.util.Date;

public record ResponseAuth(Date exp, String token) {}

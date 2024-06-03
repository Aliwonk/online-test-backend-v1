package ru.test_app.backend.controllers.test.controller.DTO;

import java.util.List;

public record CreateTestDTO(String title, int expires, int attempt, String close, List<CreateSectionDTO> sections) {
}

package ru.test_app.backend.controllers.test.controller.DTO;

import java.util.List;

public record CreateSectionDTO(String title, List<CreateQuestionDTO> questions) {
}

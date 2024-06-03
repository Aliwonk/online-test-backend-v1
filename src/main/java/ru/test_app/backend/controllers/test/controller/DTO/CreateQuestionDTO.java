package ru.test_app.backend.controllers.test.controller.DTO;

import java.util.List;

public record CreateQuestionDTO(String text, int scope, List<CreateOptionDTO> options) {
}

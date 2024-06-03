package ru.test_app.backend.controllers.test.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test_app.backend.controllers.test.database.entity.QuestionOptionsEntity;

import java.util.UUID;

public interface QuestionOptionsEntityRepository extends CrudRepository<QuestionOptionsEntity, UUID> {
}

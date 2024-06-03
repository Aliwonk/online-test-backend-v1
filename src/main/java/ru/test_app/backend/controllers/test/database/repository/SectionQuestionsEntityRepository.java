package ru.test_app.backend.controllers.test.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test_app.backend.controllers.test.database.entity.SectionQuestionsEntity;

import java.util.UUID;

public interface SectionQuestionsEntityRepository extends CrudRepository<SectionQuestionsEntity, UUID> {
}

package ru.test_app.backend.controllers.test.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test_app.backend.controllers.test.database.entity.TestSectionsEntity;

import java.util.UUID;

public interface TestSectionEntityRepository extends CrudRepository<TestSectionsEntity, UUID> {
}

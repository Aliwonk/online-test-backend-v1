package ru.test_app.backend.controllers.test.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test_app.backend.controllers.test.database.entity.TestEntity;

import java.util.UUID;

public interface TestEntityRepository extends CrudRepository<TestEntity, UUID> {
}

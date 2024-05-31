package ru.test_app.backend.controllers.user.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test_app.backend.controllers.user.database.entity.RoleEntity;

import java.util.UUID;

public interface RoleEntityRepository extends CrudRepository<RoleEntity, UUID> {
}

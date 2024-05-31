package ru.test_app.backend.controllers.user.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.test_app.backend.controllers.user.database.entity.UserEntity;
import java.util.UUID;

public interface UserEntityRepository extends CrudRepository<UserEntity, UUID> {
}

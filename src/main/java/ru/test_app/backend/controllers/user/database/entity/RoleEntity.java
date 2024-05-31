package ru.test_app.backend.controllers.user.database.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Entity(name = "user_roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private ROLES role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /* CONSTRUCTORS */

    public RoleEntity() {}

    public RoleEntity(ROLES role) {
        this.role = role;
    }

    public RoleEntity(ROLES role, UserEntity user) {
        this.role = role;
        this.user = user;
    }


    /* GETTERS */

    public UUID getId() {
        return id;
    }

    public ROLES getRole() {
        return role;
    }

    public UserEntity getUser() {
        return user;
    }

    /* SETTERS */

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

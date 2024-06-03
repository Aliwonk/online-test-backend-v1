package ru.test_app.backend.controllers.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.test_app.backend.controllers.auth.controller.DTO.CreateUserDTO;
import ru.test_app.backend.controllers.response_types.ResponseService;
import ru.test_app.backend.controllers.user.database.entity.ROLES;
import ru.test_app.backend.controllers.user.database.entity.RoleEntity;
import ru.test_app.backend.controllers.user.database.entity.UserEntity;
import ru.test_app.backend.controllers.user.database.repository.RoleEntityRepository;
import ru.test_app.backend.controllers.user.database.repository.UserEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private RoleEntityRepository roleRepository;

    public ResponseService createUser(CreateUserDTO userDTO) {
        final ResponseService responseService = new ResponseService();
        try {
            /* CREATE USER ENTITY AND ROLE ENTITY */

            UserEntity userEntity = new UserEntity(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.getHashPassword());
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(new RoleEntity(ROLES.USER, userEntity));
            userEntity.setRoles(roles);

            /* SAVE ENTITY IN DB AND SEND RESULT */

            UserEntity user = userRepository.save(userEntity);
            responseService.setStatusCode(HttpStatus.CREATED.value());
            responseService.setData(user);
            return responseService;
        } catch (Exception ex) {
            final String errMessage = ex.getMessage();

            if(errMessage.contains("повторяющееся значение ключа")) {
                responseService.setStatusCode(HttpStatus.CONFLICT.value());
                responseService.setErr("User already exists");
                responseService.setData(null);
            } else {
                responseService.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            System.out.printf("Error in method create user: %s", ex.getMessage());
            return responseService;
        }
    }

    public ResponseService getUserByEmail(String email) {
        final ResponseService responseService = new ResponseService();
        try {
            final Optional<UserEntity> user = this.userRepository.findByEmail(email);

            if(user.isPresent()) {
                responseService.setStatusCode(HttpStatus.OK.value());
                responseService.setData(user.get());
            } else {
                responseService.setStatusCode(HttpStatus.NOT_FOUND.value());
                responseService.setErr("User not found");
            }
            return responseService;
        } catch (Exception ex) {
            return responseService;
        }
    }

    public ResponseService getProfileByUUID(UUID id) {
        final ResponseService responseService = new ResponseService();
        try {
            final Optional<UserEntity> user = this.userRepository.findById(id);

            if(user.isPresent()) {
                responseService.setStatusCode(HttpStatus.OK.value());
                responseService.setData(user.get());
            } else {
                responseService.setStatusCode(HttpStatus.NOT_FOUND.value());
                responseService.setErr("User not found");
            }
            return responseService;
        } catch (Exception ex) {
            responseService.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return responseService;
        }
    }

    public String updateUser() {
        return "User Updated";
    }

    public ResponseService addRole(ROLES role, UUID userId) {
        final ResponseService responseService = new ResponseService();

        try {
            Optional<UserEntity> user = this.userRepository.findById(userId);

            if(user.isPresent()) {
                for(RoleEntity roleEntity : user.get().getRoles()) {
                    if (roleEntity.getRole().equals(role)) {
                        responseService.setStatusCode(HttpStatus.CONFLICT.value());
                        responseService.setErr("Role already exists");
                        return responseService;
                    }
                }

                RoleEntity roleEntity = new RoleEntity(role, user.get());
                user.get().addRole(roleEntity);

                this.roleRepository.save(roleEntity);
                responseService.setStatusCode(HttpStatus.CREATED.value());
                responseService.setData("User role created");
            } else {
                responseService.setStatusCode(HttpStatus.NOT_FOUND.value());
                responseService.setErr("User not found");
            }
            return responseService;
        } catch (Exception ex) {
            responseService.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseService.setErr(ex.getMessage());
            return responseService;
        }
    }

    public ResponseService deleteRole(UUID roleId) {
        final ResponseService responseService = new ResponseService();

        try {
            final Optional<RoleEntity> roleEntity = this.roleRepository.findById(roleId);

            if(roleEntity.isPresent()) {
                this.roleRepository.delete(roleEntity.get());
                responseService.setStatusCode(HttpStatus.OK.value());
                responseService.setData("User role deleted");
            } else {
                responseService.setStatusCode(HttpStatus.NOT_FOUND.value());
                responseService.setErr("Role not found");
            }
            return responseService;
        } catch (Exception ex) {
            responseService.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseService.setErr(ex.getMessage());
            return responseService;
        }
    }

    public String deleteUser(UUID id) {
        return "User Deleted";
    }

}

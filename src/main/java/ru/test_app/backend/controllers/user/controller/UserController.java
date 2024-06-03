package ru.test_app.backend.controllers.user.controller;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test_app.backend.controllers.response_types.ResponseController;
import ru.test_app.backend.controllers.response_types.ResponseService;
import ru.test_app.backend.controllers.user.database.entity.ROLES;
import ru.test_app.backend.controllers.user.service.UserService;
import ru.test_app.backend.utils.jwt.JwtProvider;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping()
    public ResponseEntity<ResponseController> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        final ResponseController responseController = new ResponseController();
        try {
            final String token = authorization.substring(7);
            final UUID idUser = jwtProvider.getId(token);
            final ResponseService responseService = this.userService.getProfileByUUID(idUser);

            responseController.setStatusCode(responseService.getStatusCode());
            responseController.setData(responseService.getData());
            return ResponseEntity.ok(responseController);
        } catch (Exception ex) {
            System.out.printf("Error user service in method get user: %s \n", ex.getMessage());
            return ResponseEntity.internalServerError().body(responseController);
        }
    }

    @PatchMapping()
    public String patchUser() {
        return this.userService.updateUser();
    }

    @PatchMapping("/add-role")
    public ResponseEntity<ResponseController> updateRole(@RequestBody AddRoleDTO roleDTO) {
        final ResponseController responseController = new ResponseController();

        try {
            final UUID userId = UUID.fromString(roleDTO.userID());

            for (ROLES role : ROLES.values()) {
                if (role.name().equals(roleDTO.role())) {
                    final ResponseService resultAddRole = this.userService.addRole(role, userId);

                    responseController.setStatusCode(resultAddRole.getStatusCode());

                    if(resultAddRole.getStatusCode() == HttpStatus.CREATED.value()) {
                        responseController.setData(resultAddRole.getData());
                    } else  {
                        responseController.setErrorMessage(resultAddRole.getErr());
                    }
                    return ResponseEntity.status(resultAddRole.getStatusCode()).body(responseController);
                }
            }


            responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseController.setErrorMessage("Invalid role; Valid roles: TEACHER, STUDENT");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(responseController);
        }
    }

    @PatchMapping("/delete-role/{id}")
    public ResponseEntity<ResponseController> deleteRole(@PathVariable String id) {
        final ResponseController responseController = new ResponseController();

        try {
            UUID idUser = UUID.fromString(id);
            final ResponseService resultDelete = this.userService.deleteRole(idUser);

            responseController.setStatusCode(resultDelete.getStatusCode());

            if(resultDelete.getStatusCode() != 404) {
                responseController.setData(resultDelete.getData());
            } else {
                responseController.setErrorMessage(resultDelete.getErr());
            }
            return ResponseEntity.status(resultDelete.getStatusCode()).body(responseController);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(responseController);
        }
    }
}

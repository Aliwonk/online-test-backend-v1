package ru.test_app.backend.controllers.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test_app.backend.controllers.auth.controller.DTO.CreateUserDTO;
import ru.test_app.backend.controllers.auth.controller.DTO.LoginUserDTO;
import ru.test_app.backend.controllers.auth.service.AuthService;
import ru.test_app.backend.controllers.response_types.ResponseController;
import ru.test_app.backend.controllers.response_types.ResponseService;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<ResponseController> login(@RequestBody LoginUserDTO userDTO) {
        final ResponseController responseController = new ResponseController();
        try {
            ResponseService resultLogin = authService.login(userDTO);
            responseController.setData(resultLogin.getData());
            responseController.setStatusCode(resultLogin.getStatusCode());

            if(resultLogin.getErr() != null) {
                responseController.setErrorMessage(resultLogin.getErr());
            }
            return ResponseEntity.status(resultLogin.getStatusCode()).body(responseController);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseController);
        }
    }

    @PostMapping("register")
    public ResponseEntity<ResponseController> register(@RequestBody CreateUserDTO userDTO) {
        final ResponseController responseController = new ResponseController();
        try {
            if(this.checkValidUserData(userDTO) != null) return this.checkValidUserData(userDTO);

            ResponseService resultCreateUser = authService.register(userDTO);
            if(resultCreateUser.getErr() != null) {
                responseController.setErrorMessage(resultCreateUser.getErr());
            } else {
                responseController.setData(resultCreateUser.getData());
            }
            responseController.setStatusCode(resultCreateUser.getStatusCode());
            return ResponseEntity.status(resultCreateUser.getStatusCode()).body(responseController);

        } catch (Exception e) {
            responseController.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseController.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseController);
        }
    }

    private ResponseEntity<ResponseController> checkValidUserData(CreateUserDTO userDTO) {
        final ResponseController responseController = new ResponseController();
        if(!userDTO.checkFullName()) {

            responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseController.setErrorMessage("Empty first name or last name");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);

        } else if(!userDTO.checkValidEmail()) {

            responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseController.setErrorMessage("Invalid email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);

        } else if(!userDTO.checkValidPassword()) {

            responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseController.setErrorMessage("Invalid password; Must be at least 6 characters or not null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);

        }

        return null;
    }
}

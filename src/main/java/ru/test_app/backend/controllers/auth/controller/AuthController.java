package ru.test_app.backend.controllers.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test_app.backend.controllers.auth.controller.DTO.CreateUserDTO;
import ru.test_app.backend.controllers.auth.controller.DTO.LoginUserDTO;
import ru.test_app.backend.controllers.auth.service.AuthService;
import ru.test_app.backend.controllers.response_types.ResponseController;

@RestController
@RequestMapping("auth")
public class AuthController {
//    private final ResponseController responseController = new ResponseController();

    @Autowired
    private AuthService authService;


    @GetMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginUserDTO userDTO) {
        final ResponseController responseController = new ResponseController();
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("register")
    public ResponseEntity<ResponseController> register(@RequestBody CreateUserDTO userDTO) {
        final ResponseController responseController = new ResponseController();
        try {
            if(!userDTO.checkFullName()) {

                responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseController.setMessage("Empty first name or last name");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);

            } else if(!userDTO.checkValidEmail()) {

                responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseController.setMessage("Invalid email");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);

            } else if(!userDTO.checkValidPassword()) {

                responseController.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseController.setMessage("Invalid password; Must be at least 6 characters or not null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseController);

            } else {

                responseController.setStatusCode(HttpStatus.CREATED.value());
                responseController.setData(authService.register(userDTO).getData());
                return ResponseEntity.status(HttpStatus.CREATED).body(responseController);

            }

        } catch (Exception e) {
            responseController.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseController.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseController);
        }
    }
}

package ru.test_app.backend.controllers.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test_app.backend.controllers.response_types.ResponseController;
import ru.test_app.backend.controllers.response_types.ResponseService;
import ru.test_app.backend.controllers.test.controller.DTO.CreateTestDTO;
import ru.test_app.backend.controllers.test.service.TestService;
import ru.test_app.backend.utils.jwt.JwtProvider;

import java.util.UUID;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestService testService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping()
    public ResponseEntity<String> getTest() {
        final ResponseController responseController = new ResponseController();

        try {

            return ResponseEntity.ok("GET TEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @PostMapping()
    public ResponseEntity<ResponseController> postTest(@RequestHeader("Authorization") String auth, @RequestBody CreateTestDTO testDTO) {
        final ResponseController responseController = new ResponseController();

        try {
            final String token = auth.substring(7);
            final UUID userID = jwtProvider.getId(token);
            ResponseService resultCreate = this.testService.createTest(testDTO, userID);

            if(resultCreate.getStatusCode() == HttpStatus.CREATED.value()) {
                responseController.setData(resultCreate.getData());
            } else {
                responseController.setErrorMessage(resultCreate.getErr());
            }

            responseController.setStatusCode(resultCreate.getStatusCode());
            return ResponseEntity.status(resultCreate.getStatusCode()).body(responseController);
        } catch (Exception ex) {
            System.out.printf("Error test controller in method create test: %s \n", ex.getMessage());
            responseController.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseController);
        }
    }

    @PatchMapping()
    public ResponseEntity<String> patchTest() {
        return ResponseEntity.ok("PATCH TEST");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteTest() {
        return ResponseEntity.ok("DELETE TEST");
    }
}

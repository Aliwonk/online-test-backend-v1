package ru.test_app.backend.controllers.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.test_app.backend.controllers.response_types.ResponseService;
import ru.test_app.backend.controllers.test.controller.DTO.CreateTestDTO;
import ru.test_app.backend.controllers.test.database.entity.TestEntity;
import ru.test_app.backend.controllers.test.database.repository.QuestionOptionsEntityRepository;
import ru.test_app.backend.controllers.test.database.repository.SectionQuestionsEntityRepository;
import ru.test_app.backend.controllers.test.database.repository.TestEntityRepository;
import ru.test_app.backend.controllers.test.database.repository.TestSectionEntityRepository;
import ru.test_app.backend.controllers.user.database.entity.UserEntity;
import ru.test_app.backend.controllers.user.database.repository.UserEntityRepository;

import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestService {
    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private TestEntityRepository testRepository;

    @Autowired
    private TestSectionEntityRepository testSectionRepository;

    @Autowired
    private SectionQuestionsEntityRepository sectionQuestionsRepository;

    @Autowired
    private QuestionOptionsEntityRepository questionOptionsRepository;

    public ResponseService createTest(CreateTestDTO testDTO, UUID creatorID) {
        final ResponseService responseService = new ResponseService();

        try {
            final Optional<UserEntity> creator = this.userRepository.findById(creatorID);

            if(creator.isEmpty()) {
                responseService.setStatusCode(HttpStatus.NOT_FOUND.value());
                responseService.setErr("Creator test not found");
                return responseService;
            }

            final LocalTime expiresTest = LocalTime.now().plusMinutes(testDTO.expires());
//            final TestEntity testEntity = new TestEntity(testDTO.title(), expiresTest);
            System.out.printf("Expires test: %s \n", expiresTest);

            responseService.setStatusCode(HttpStatus.OK.value());
            responseService.setData("Test Data");
            return responseService;
        } catch (Exception ex) {
            responseService.setStatusCode(500);
            return responseService;
        }
    }
}

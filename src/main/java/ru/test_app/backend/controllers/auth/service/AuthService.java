package ru.test_app.backend.controllers.auth.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test_app.backend.controllers.auth.controller.DTO.CreateUserDTO;
import ru.test_app.backend.controllers.auth.controller.DTO.LoginUserDTO;
import ru.test_app.backend.controllers.response_types.ResponseService;
import ru.test_app.backend.controllers.user.database.entity.UserEntity;
import ru.test_app.backend.controllers.user.service.UserService;
import ru.test_app.backend.utils.jwt.JwtProvider;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    public String login(LoginUserDTO userDTO) {
        return userDTO.email();
    }

    public ResponseService register(CreateUserDTO userDTO) {
        final ResponseService responseService = new ResponseService();
        try {
            final ResponseService createUser = this.userService.createUser(userDTO);

            if(createUser.getData() instanceof UserEntity userEntity) {
                final String jwtToken = jwtProvider.generateToken(userEntity);
                responseService.setData(new ResponseAuth(jwtProvider.getAccessExpiration(), jwtToken));
            }

            return responseService;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return responseService;
        }
    }
}

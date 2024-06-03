package ru.test_app.backend.controllers.auth.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.test_app.backend.controllers.auth.controller.DTO.CreateUserDTO;
import ru.test_app.backend.controllers.auth.controller.DTO.LoginUserDTO;
import ru.test_app.backend.controllers.response_types.ResponseService;
import ru.test_app.backend.controllers.user.database.entity.UserEntity;
import ru.test_app.backend.controllers.user.service.UserService;
import ru.test_app.backend.utils.bcrypt.BcryptProvider;
import ru.test_app.backend.utils.jwt.JwtProvider;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    public ResponseService login(LoginUserDTO userDTO) {
        final ResponseService responseService = new ResponseService();
        try {
            ResponseService resultGetUser = this.userService.getUserByEmail(userDTO.login());

            if(resultGetUser.getErr() != null) {
                responseService.setErr(resultGetUser.getErr());
                responseService.setStatusCode(resultGetUser.getStatusCode());
            } else {
                UserEntity user = (UserEntity) resultGetUser.getData();
                final boolean verifyPassword = BcryptProvider.verify(userDTO.password(), user.getHashedPassword());

                if(verifyPassword) {
                    final String jwtToken = jwtProvider.generateToken(user);
                    responseService.setData(new ResponseAuth(jwtProvider.getAccessExpiration(), jwtToken));
                    responseService.setStatusCode(resultGetUser.getStatusCode());
                } else {
                    responseService.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    responseService.setErr("Invalid password or email");
                }
            }

            return responseService;
        } catch (Exception ex) {
            responseService.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return responseService;
        }
    }

    public ResponseService register(CreateUserDTO userDTO) {
        final ResponseService responseService = new ResponseService();
        try {
            final ResponseService resultCreateUser = this.userService.createUser(userDTO);
            final UserEntity userEntity = (UserEntity) resultCreateUser.getData();

            if(resultCreateUser.getStatusCode() == HttpStatus.CREATED.value()) {
                final String jwtToken = jwtProvider.generateToken(userEntity);
                responseService.setStatusCode(resultCreateUser.getStatusCode());
                responseService.setData(new ResponseAuth(jwtProvider.getAccessExpiration(), jwtToken));
            } else {
                responseService.setStatusCode(resultCreateUser.getStatusCode());
                responseService.setErr(resultCreateUser.getErr());
            }

            return responseService;
        } catch (Exception ex) {
            System.out.printf("Error in authService: %s", ex.getMessage());
            responseService.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return responseService;
        }
    }
}

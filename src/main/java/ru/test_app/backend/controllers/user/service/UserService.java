package ru.test_app.backend.controllers.user.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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
            List<ROLES> roles = new ArrayList<>();
            roles.add(ROLES.USER);
            List<RoleEntity> userRoles = this.createUserRoles(roles);

//            if(userRoles != null) {
//                UserEntity userEntity = new UserEntity(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.getHashPassword(), userRoles);
//                UserEntity user = this.userRepository.save(userEntity);
//
//                responseService.setData(user);
//            }

            return responseService;
        } catch (Exception ex) {
            System.out.printf("Error in method create user: %s", ex.getMessage());
            return responseService;
        }
    }

    public UserEntity getProfile(UUID id) {
        return this.userRepository.findById(id).get();
    }

    public String updateUser() {
        return "User Updated";
    }

    public String deleteUser(UUID id) {
        return "User Deleted";
    }

    public List<RoleEntity> createUserRoles(List<ROLES> roles) {
        try {
            final List<RoleEntity> userRoles = new ArrayList<>();

            for (ROLES role : roles) {
                final RoleEntity roleEntity = this.roleRepository.save(new RoleEntity(role));
                userRoles.add(roleEntity);
            }
            return userRoles;
        } catch (Exception ex) {
            System.out.printf("Error in method create user role: %s", ex.getMessage());
            return null;
        }
    }
}

package com.nisum.api.service;

import com.nisum.api.dao.OperationsDao;
import com.nisum.api.dto.request.UserRequest;
import com.nisum.api.dto.response.UserResponse;
import com.nisum.api.entity.User;
import com.nisum.api.exception.NotFoundException;
import com.nisum.api.exception.UnprocessableEntity;
import com.nisum.api.mapper.UserMapper;
import com.nisum.api.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final OperationsDao<User> userOperationsDao;

    private final UserMapper userMapper;

    public UserResponse saveUser(UserRequest request) {
        validateUserByEmail(request.getEmail());

        String password = passwordEncoder.encode(request.getPassword());
        request.setPassword(password);

        String generatedUUID = Util.generateId();
        String token = tokenService.generateTokenByUserType(request.getEmail());

        User user = userMapper.buildUserEntity(request, generatedUUID, token);

        log.info("Save user: {}", user);

        return userMapper.buildUserResponse(userOperationsDao.saveOrUpdate(user));
    }

    @Transactional(readOnly = true)
    public UserResponse findUserById(String id) {
        return userMapper.buildUserResponse(validateExistsUserById(id));
    }

    public UserResponse updateUser(String id, UserRequest request) {
        User user = validateExistsUserById(id);

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        log.info("Updating user. ID: {}, Request: {}", id, request);

        return userMapper.buildUserResponse(userOperationsDao.saveOrUpdate(user));
    }

    public void deleteUser(String id) {
        User user = validateExistsUserById(id);

        log.info("Delete user. ID: {}", id);
        userOperationsDao.delete(user);
    }

    private User validateExistsUserById(String id) {
        return userOperationsDao.findOne("id", id)
                .orElseThrow(() -> new NotFoundException("No se encontro al Usuario " + id));
    }

    private void validateUserByEmail(String email) {
        userOperationsDao.findOne("email", email).ifPresent(user -> {
            throw new UnprocessableEntity("El correo ya registrado");
        });
    }
}

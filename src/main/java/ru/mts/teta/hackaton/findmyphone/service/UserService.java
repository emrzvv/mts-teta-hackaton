package ru.mts.teta.hackaton.findmyphone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mts.teta.hackaton.findmyphone.dao.UserRepository;
import ru.mts.teta.hackaton.findmyphone.domain.User;

@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(Long id, Boolean isAdmin, String token, String deviceId) {
        userRepository.save(new User(
                id,
                isAdmin,
                token,
                deviceId
        ));
    }
}

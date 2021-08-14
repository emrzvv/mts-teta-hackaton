package ru.mts.teta.hackaton.findmyphone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.teta.hackaton.findmyphone.domain.dto.NewUserDto;
import ru.mts.teta.hackaton.findmyphone.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/push")
public class PhoneToServerController {
    @Autowired
    private final UserService userService;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final AtomicLong identity = new AtomicLong();

    @Autowired
    public PhoneToServerController(UserService userService) {
        this.userService = userService;
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @PostMapping(value = "/new_child", consumes = "application/json")
    public String generateNewChild(@RequestBody NewUserDto newUser) {
        String token = generateNewToken();
        userService.saveUser(
                identity.incrementAndGet(),
                false,
                token,
                newUser.getDeviceId()
        );
        return token;
    }
}

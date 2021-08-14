package ru.mts.teta.hackaton.findmyphone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mts.teta.hackaton.findmyphone.domain.dto.NewUserDto;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.service.RecordService;
import ru.mts.teta.hackaton.findmyphone.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/push")
public class PhoneToServerController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final RecordService recordService;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final AtomicLong identity = new AtomicLong();

    @Autowired
    public PhoneToServerController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
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

    @PostMapping(value = "/metrics/one", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void pushOne(@RequestBody RecordDto recordDto) {
        recordService.saveRecord(recordDto);
    }

    @PostMapping(value = "/metrics/many", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void pushMany(@RequestBody List<RecordDto> records) {
        records.forEach(recordService::saveRecord);
    }
}

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
public class PushController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final RecordService recordService;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private final AtomicLong identity = new AtomicLong();
    private final AtomicLong recordIdentity = new AtomicLong();

    @Autowired
    public PushController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @PostMapping(value = "/new_child")
    public String generateNewChild(@RequestBody String deviceId) {
        String token = generateNewToken();
        userService.saveUser(
                identity.incrementAndGet(),
                false,
                token,
                deviceId
        );
        return token;
    }

    @PostMapping(value = "/metrics/one", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void pushOne(@RequestBody RecordDto recordDto) {
        recordDto.setId(recordIdentity.incrementAndGet());
        recordService.saveRecord(recordDto);
    }

    @PostMapping(value = "/metrics/many", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void pushMany(@RequestBody List<RecordDto> records) {
        records.forEach(recordService::saveRecord);
    }
}

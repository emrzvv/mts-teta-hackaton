package ru.mts.teta.hackaton.findmyphone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.service.RecordService;

@RestController
@RequestMapping("/get")
public class GetController {
    @Autowired
    private final RecordService recordService;

    @Autowired
    public GetController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{token}/last_record")
    @ResponseBody
    public RecordDto getLastRecord(@PathVariable("token") String token) throws Exception {
        RecordDto rdto = recordService.getLastRecord(token);
        System.out.println(rdto.getDate() + " " + rdto.getFromToken());
        return rdto;
    }
}

package ru.mts.teta.hackaton.findmyphone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.service.RecordService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/get")
public class GetController {
    @Autowired
    private final RecordService recordService;

    private static final LocalDateTime minTime = LocalDateTime.of(0, 1, 1, 0, 0, 0, 1);
    private static final LocalDateTime maxTime = LocalDateTime.of(9999, 12, 31, 23, 59, 59, 59);

    @Autowired
    public GetController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{token}/last_record")
    @ResponseBody
    public RecordDto getLastRecord(@PathVariable("token") String token) throws Exception {
        RecordDto rdto = recordService.getLastRecord(token);
        return rdto;
    }

    @GetMapping("/{token}/records")
    @ResponseBody
    public List<RecordDto> getRecords(@PathVariable("token") String token, //
                                      @RequestParam(value = "time_begin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  LocalDateTime timeBegin,
                                      @RequestParam(value = "time_end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime timeEnd) {
        if (timeBegin == null) {
            timeBegin = minTime;
        }
        if (timeEnd == null) {
            timeEnd = maxTime;
        }

        return recordService.getRecords(token, timeBegin, timeEnd);
    }
}

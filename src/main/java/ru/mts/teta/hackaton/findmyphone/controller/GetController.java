package ru.mts.teta.hackaton.findmyphone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.mts.teta.hackaton.findmyphone.config.Constants;
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

    @Autowired
    public GetController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{token}/last_record")
    @ResponseBody
    public RecordDto getLastRecord(@PathVariable("token") String token) throws Exception {
        return recordService.getLastRecord(token);
    }

    @GetMapping("/{token}/records")
    @ResponseBody
    public List<RecordDto> getRecords(@PathVariable("token") String token, //
                                      @RequestParam(value = "time_begin", required = false) @DateTimeFormat(pattern = Constants.timeFormat)  LocalDateTime timeBegin,
                                      @RequestParam(value = "time_end", required = false) @DateTimeFormat(pattern = Constants.timeFormat) LocalDateTime timeEnd) {
        if (timeBegin == null) {
            timeBegin = Constants.minTime;
        }
        if (timeEnd == null) {
            timeEnd = Constants.maxTime;
        }

        return recordService.getRecords(token, timeBegin, timeEnd);
    }
}

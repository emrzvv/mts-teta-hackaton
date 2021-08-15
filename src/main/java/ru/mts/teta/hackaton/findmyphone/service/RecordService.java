package ru.mts.teta.hackaton.findmyphone.service;

import ru.mts.teta.hackaton.findmyphone.dao.RecordRepository;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.Record;
import ru.mts.teta.hackaton.findmyphone.domain.dto.convert.ConverterRecordDto;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Component
public class RecordService {
	public RecordRepository recordRepository;

	@Autowired
    public RecordService(RecordRepository recordRepository) { 
        this.recordRepository = recordRepository;
    }

    public List<RecordDto> getRecordsBySearchString(String searchString, Long page) {
    	if (searchString.equals("")) {
            List<Record> records = recordRepository.findAll();
            page = Long.valueOf(records.size());
    		return ConverterRecordDto.fromEntitiesToDtos(records);
    	}

    	/*
    	Заглушка
    	*/

        List<Record> records = recordRepository.findAll();
        page = Long.valueOf(records.size());
    	return ConverterRecordDto.fromEntitiesToDtos(records);
    }
}
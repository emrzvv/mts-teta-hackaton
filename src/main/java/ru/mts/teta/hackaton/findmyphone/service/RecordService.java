package ru.mts.teta.hackaton.findmyphone.service;

import ru.mts.teta.hackaton.findmyphone.dao.RecordRepository;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
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

    public List<RecordDto> getRecordsBySearchString(String searchString) {
    	if (searchString.equals("")) {
    		return ConverterRecordDto.fromEntitiesToDtos(recordRepository.findAll());
    	}

    	/*
    	Заглушка
    	*/
    	return ConverterRecordDto.fromEntitiesToDtos(recordRepository.findAll());
    }
}
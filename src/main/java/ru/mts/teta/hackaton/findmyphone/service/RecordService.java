package ru.mts.teta.hackaton.findmyphone.service;

import ru.mts.teta.hackaton.findmyphone.dao.RecordRepository;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.Record;
import ru.mts.teta.hackaton.findmyphone.domain.dto.convert.ConverterRecordDto;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

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

    public void saveRecord(RecordDto recordDto) {
		recordRepository.save(ConverterRecordDto.fromDtoToEntity(recordDto));
	}

	public RecordDto getLastRecord(String token) throws Exception {
		return ConverterRecordDto.fromEntityToDto(
				recordRepository.getLastRecord(token).orElseThrow(() -> new Exception("No such data for this token")));
	}

	public List<RecordDto> getRecords(String token, LocalDateTime timeBegin, LocalDateTime timeEnd) {
		return ConverterRecordDto.fromEntitiesToDtos(recordRepository.getByTimeInterval(token, timeBegin, timeEnd));
	}
}
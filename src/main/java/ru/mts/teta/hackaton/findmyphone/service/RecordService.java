package ru.mts.teta.hackaton.findmyphone.service;

import ru.mts.teta.hackaton.findmyphone.dao.RecordRepository;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.Record;
import ru.mts.teta.hackaton.findmyphone.domain.dto.convert.ConverterRecordDto;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Component
public class RecordService {
	public RecordRepository recordRepository;

	@Autowired
    public RecordService(RecordRepository recordRepository) { 
        this.recordRepository = recordRepository;
    }

    public List<RecordDto> getRecordsBySearchString(String searchString, Long page) {
    	Pageable paging = PageRequest.of(page.intValue(), 100);
    	Page<Record> records = null;
    	if (searchString.equals("")) {
            records = recordRepository.findAll(paging);
    	} else {
	    	/*
	    	Заглушка
	    	*/
	        records = recordRepository.findAll(paging);

    	}

    	if(records.hasContent()) {
    		List<Record> recordsList = records.getContent();
    		page = Long.valueOf(records.getTotalPages());
            return ConverterRecordDto.fromEntitiesToDtos(recordsList);
        }
        page = 0L;
        return new ArrayList<RecordDto>();
    }

    public void saveRecord(RecordDto recordDto) {
    	Record record = ConverterRecordDto.fromDtoToEntity(recordDto);
    	LocalDateTime timeNow = LocalDateTime.now().withNano(0);
    	record.setAddedDate(timeNow);
		recordRepository.save(record);
	}

	public RecordDto getLastRecord(String token) throws Exception {
		return ConverterRecordDto.fromEntityToDto(
				recordRepository.getLastRecord(token).orElseThrow(() -> new Exception("No such data for this token")));
	}

	public List<RecordDto> getRecords(String token, LocalDateTime timeBegin, LocalDateTime timeEnd) {
		return ConverterRecordDto.fromEntitiesToDtos(recordRepository.getByTimeInterval(token, timeBegin, timeEnd));
	}
}
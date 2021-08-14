package ru.mts.teta.hackaton.findmyphone.service;

import ru.mts.teta.hackaton.findmyphone.dao.RecordRepository;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.dto.convert.ConverterRecordDto;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

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

    public void saveRecord(RecordDto recordDto) {
		recordRepository.save(ConverterRecordDto.fromDtoToEntity(recordDto));
	}

	public RecordDto getLastRecord(String token) throws Exception {
		return ConverterRecordDto.fromEntityToDto(
				recordRepository.getLastRecord(token).orElseThrow(() -> new Exception("No such data for this token")));
	}
}
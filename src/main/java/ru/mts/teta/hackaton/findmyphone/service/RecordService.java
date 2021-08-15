package ru.mts.teta.hackaton.findmyphone.service;

import ru.mts.teta.hackaton.findmyphone.dao.RecordRepository;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.Record;
import ru.mts.teta.hackaton.findmyphone.domain.dto.convert.ConverterRecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.RecordsPage;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Component
public class RecordService {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public RecordRepository recordRepository;

	@Autowired
    public RecordService(RecordRepository recordRepository) { 
        this.recordRepository = recordRepository;
    }

    public RecordsPage getRecordsBySearchString(String searchString, Integer page) {
    	Pageable paging = PageRequest.of(page.intValue(), 100);
    	Page<Record> records = null;
    	RecordsPage recordsPage = new RecordsPage();

    	if (searchString.equals("")) {
            records = recordRepository.findAll(paging);
    	} else {
	    	Map<String, String> mp = SearchParser.parse(searchString);
	    	List<Record> recordsList = null;
	    	boolean hasToken = mp.containsKey("token");
	    	boolean hasSince = mp.containsKey("since");
	    	boolean hasBefore = mp.containsKey("before");
	    	LocalDateTime sinceTime = null;
	    	LocalDateTime beforeTime = null;
	    	System.out.println("" + hasToken + " " + hasSince + " " + hasBefore);
	    	if (hasSince) {
	    		sinceTime = LocalDateTime.parse(mp.get("since"), formatter);
	    	}
	    	if (hasBefore) {
	    		beforeTime = LocalDateTime.parse(mp.get("before"), formatter);
	    	}
	    	if (hasToken && !hasSince && !hasBefore) {
				recordsList = recordRepository.findAllByToken(mp.get("token"));
	    	} else if (!hasToken && hasSince && hasBefore) {
	    		recordsList = recordRepository.findAllByTimeInterval(sinceTime, beforeTime);
	    	} else if (hasToken && hasSince && hasBefore) {
	    		recordsList = recordRepository.getByTimeInterval(mp.get("token"), sinceTime, beforeTime);
	    	} else if (!hasToken && !hasSince && hasBefore) {
	    		recordsList = recordRepository.findAllBefore(beforeTime);
	    	} else if (!hasToken && hasSince && !hasBefore) {
	    		recordsList = recordRepository.findAllSince(sinceTime);
	    	} else if (hasToken && hasSince && !hasBefore) {
	    		recordsList = recordRepository.findAllByTokenSince(mp.get("token"), sinceTime);
	    	} else if (hasToken && !hasSince && hasBefore) {
	    		recordsList = recordRepository.findAllByTokenBefore(mp.get("token"), beforeTime);
	    	} else {
	        	records = recordRepository.findAll(paging);
	    	}
	    	if (recordsList != null) {
		        recordsPage.setTotalPages(recordsList.size() / 100 + (recordsList.size() % 100 != 0 ? 1 : 0));
		        int toDelete = Math.min((page.intValue()-1) * 100, recordsList.size());
		        for (int i=0; i<toDelete; ++i) {
		        	recordsList.remove(i);
		        }
		        for (int i=100; i<recordsList.size(); ++i) {
		        	recordsList.remove(i);
		        }
		        recordsPage.setRecords(ConverterRecordDto.fromEntitiesToDtos(recordsList));
		        return recordsPage;
		    }
    	}

    	if(records.hasContent()) {
    		recordsPage.setTotalPages(records.getTotalPages());
    		recordsPage.setRecords(ConverterRecordDto.fromEntitiesToDtos(records.getContent()));
        }else{
        	recordsPage.setTotalPages(0);
    		recordsPage.setRecords(new ArrayList<RecordDto>());
        }
        return recordsPage;
    }

    public RecordDto findById(Long recordId) throws Exception{
    	return ConverterRecordDto.fromEntityToDto(recordRepository.findById(recordId).orElseThrow(Exception::new));
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
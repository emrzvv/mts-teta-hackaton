package ru.mts.teta.hackaton.findmyphone.domain.dto.convert;

import ru.mts.teta.hackaton.findmyphone.domain.Record;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;

import java.util.List;
import java.util.stream.Collectors;


public class ConverterRecordDto {
	public static RecordDto fromEntityToDto(Record entity) {
		RecordDto dto = new RecordDto();
		dto.setId(entity.getId());
		dto.setFromToken(entity.getFromToken());
		dto.setAddedDate(entity.getAddedDate());
		dto.setDate(entity.getRecordDate());
		dto.setCellId(entity.getCellId());
		dto.setLac(entity.getLac());
		dto.setLatitude(entity.getLatitude());
		dto.setLongitude(entity.getLongitude());
		dto.setRsrp(entity.getRsrp());
		dto.setRsrq(entity.getRsrq());
		dto.setSinr(entity.getSinr());
		return dto;
	}

	public static Record fromDtoToEntity(RecordDto dto) {
		Record entity = new Record();
		entity.setId(dto.getId());
		entity.setFromToken(dto.getFromToken());
		entity.setAddedDate(dto.getAddedDate());
		entity.setRecordDate(dto.getDate());
		entity.setCellId(dto.getCellId());
		entity.setLac(dto.getLac());
		entity.setLatitude(dto.getLatitude());
		entity.setLongitude(dto.getLongitude());
		entity.setRsrp(dto.getRsrp());
		entity.setRsrq(dto.getRsrq());
		entity.setSinr(dto.getSinr());
		return entity;
	}

	public static List<RecordDto> fromEntitiesToDtos (List<Record> entities) {
		return entities.stream()
				.map(record -> ConverterRecordDto.fromEntityToDto(record))
	           	.collect(Collectors.toList());
	}
}
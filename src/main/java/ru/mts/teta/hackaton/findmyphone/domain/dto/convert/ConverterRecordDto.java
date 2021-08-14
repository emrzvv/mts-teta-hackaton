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
		dto.setDate(entity.getDate());
		return dto;
	}

	public static Record fromDtoToEntity(RecordDto dto) {
		Record entity = new Record();
		entity.setId(dto.getId());
		entity.setFromToken(dto.getFromToken());
		entity.setDate(dto.getDate());
		entity.setCellId(dto.getCellId());
		entity.setLac(dto.getLac());
		entity.setLatitude(dto.getLatitude());
		entity.setLongtitude(dto.getLongtitude());
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
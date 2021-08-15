package ru.mts.teta.hackaton.findmyphone.domain;

import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class RecordsPage {
	private List<RecordDto> records;
	private int totalPages;
}
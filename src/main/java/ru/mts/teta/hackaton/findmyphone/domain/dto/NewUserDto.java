package ru.mts.teta.hackaton.findmyphone.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class NewUserDto {
    private String deviceId;
}

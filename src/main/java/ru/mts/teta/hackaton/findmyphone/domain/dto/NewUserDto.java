package ru.mts.teta.hackaton.findmyphone.domain.dto;

public class NewUserDto {
    private String deviceId;

    public NewUserDto(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

package ru.mts.teta.hackaton.findmyphone.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Objects;


@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class RecordDto {

    private Long id;

    private String fromToken;

    private LocalDateTime date;

    private Double latitude;

    private Double longtitude;
    
    private Long cellId;

    private Long lac;

    private Long rsrp;
  
    private Long rsrq;

    private Long sinr;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      RecordDto record = (RecordDto) o;
      return id == record.id;
    }
  
    @Override
    public int hashCode() {
      return Objects.hash(this.id);
    }
}
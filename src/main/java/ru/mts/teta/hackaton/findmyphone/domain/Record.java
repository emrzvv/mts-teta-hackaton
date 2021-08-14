package ru.mts.teta.hackaton.findmyphone.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Lob;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDateTime;
import java.util.Objects;


@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="records")
public class Record {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fromToken;

    @Column
    private LocalDateTime date;

    @Column
    private Double latitude;

    @Column
    private Double longtitude;

    @Column
    private Long cellId;

    @Column
    private Long lac;

    @Column
    private Long rsrp;
  
    @Column
    private Long rsrq;

    @Column
    private Long sinr;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Record record = (Record) o;
      return id == record.id;
    }
  
    @Override
    public int hashCode() {
      return Objects.hash(this.id);
    }
}
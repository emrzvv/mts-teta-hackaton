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
import java.util.Objects;


@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="users")
public class User {

 	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean isAdmin;

    @Column
    private String token;

    @Column
    private Long cellId;

    @Column
    private String deviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
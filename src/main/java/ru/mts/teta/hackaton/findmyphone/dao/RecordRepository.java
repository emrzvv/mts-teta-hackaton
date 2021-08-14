package ru.mts.teta.hackaton.findmyphone.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mts.teta.hackaton.findmyphone.domain.Record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT r FROM Record r WHERE r.fromToken = :token AND r.date = (SELECT MAX(r2.date)" +
            "FROM Record r2 WHERE r.fromToken = r2.fromToken)")
    Optional<Record> getLastRecord(@Param("token") String token);
}
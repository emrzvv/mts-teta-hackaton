package ru.mts.teta.hackaton.findmyphone.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mts.teta.hackaton.findmyphone.domain.Record;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface RecordRepository extends PagingAndSortingRepository<Record, Long> {

    @Query("SELECT r FROM Record r WHERE r.fromToken = :token AND r.recordDate = (SELECT MAX(r2.recordDate)" +
            "FROM Record r2 WHERE r.fromToken = r2.fromToken)")
    Optional<Record> getLastRecord(@Param("token") String token);

    @Query("SELECT r FROM Record r WHERE r.fromToken = :token " +
            "AND :timeBegin <= r.recordDate AND r.recordDate <= :timeEnd")
    List<Record> getByTimeInterval(@Param("token") String token,
                                   @Param("timeBegin") LocalDateTime timeBegin,
                                   @Param("timeEnd") LocalDateTime timeEnd);

    @Query("SELECT r FROM Record r WHERE r.fromToken = :token")
    List<Record> findAllByToken(@Param("token") String token);

    @Query("SELECT r FROM Record r WHERE " +
            ":timeBegin <= r.recordDate AND r.recordDate <= :timeEnd")
    List<Record> findAllByTimeInterval(@Param("timeBegin") LocalDateTime timeBegin,
                                        @Param("timeEnd") LocalDateTime timeEnd);


    @Query("SELECT r FROM Record r WHERE r.fromToken = :token " +
            "AND :timeBegin <= r.recordDate")
    List<Record> findAllByTokenSince(@Param("token") String token,
                                   @Param("timeBegin") LocalDateTime timeBegin);

    @Query("SELECT r FROM Record r WHERE r.fromToken = :token " +
            "AND r.recordDate <= :timeEnd")
    List<Record> findAllByTokenBefore(@Param("token") String token,
                                    @Param("timeEnd") LocalDateTime timeEnd);

    @Query("SELECT r FROM Record r WHERE :timeBegin <= r.recordDate")
    List<Record> findAllSince(@Param("timeBegin") LocalDateTime timeBegin);

    @Query("SELECT r FROM Record r WHERE r.recordDate <= :timeEnd")
    List<Record> findAllBefore(@Param("timeEnd") LocalDateTime timeEnd);
}
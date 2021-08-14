package ru.mts.teta.hackaton.findmyphone.dao;

import ru.mts.teta.hackaton.findmyphone.domain.Record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
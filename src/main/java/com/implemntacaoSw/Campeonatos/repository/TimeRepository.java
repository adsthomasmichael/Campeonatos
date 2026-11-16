package com.implemntacaoSw.Campeonatos.repository;

import com.implemntacaoSw.Campeonatos.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
}

package com.implemntacaoSw.Campeonatos.repository;

import com.implemntacaoSw.Campeonatos.entity.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato,Long> {

}

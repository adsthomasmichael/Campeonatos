package com.implemntacaoSw.Campeonatos.repository;

import com.implemntacaoSw.Campeonatos.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
}

package com.implemntacaoSw.Campeonatos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entidade que representa um Time.
 * - Relação 1:N com Jogador (um Time possui vários Jogadores).
 * - Relação N:N com Campeonato (um Time pode participar de vários
 *   Campeonatos; o lado "dono" da relação é Campeonato).
 */
@Entity
@Table(name = "times")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(name = "ano_fundacao")
    private Integer anoFundacao;

    @Builder.Default
    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jogador> jogadores = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "times")
    private Set<Campeonato> campeonatos = new HashSet<>();

}

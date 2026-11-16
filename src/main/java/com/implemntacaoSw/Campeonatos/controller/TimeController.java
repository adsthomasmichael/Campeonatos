package com.implemntacaoSw.Campeonatos.controller;


import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorResumoDTO;
import com.implemntacaoSw.Campeonatos.dto.time.TimeRequestDTO;
import com.implemntacaoSw.Campeonatos.dto.time.TimeResponseDTO;
import com.implemntacaoSw.Campeonatos.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/times")
@Tag(name = "Times",description = "Gerenciamento de times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService){this.timeService = timeService;}

    @GetMapping
    @Operation(summary = "Lista todos os times")
    public ResponseEntity<List<TimeResponseDTO>> listarTodos(){
        return ResponseEntity.ok(timeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um time pelo id")
    public ResponseEntity<TimeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(timeService.buscarPorId(id));
    }

    @GetMapping("/{id}/jogadores")
    @Operation(summary = "Lista os jogadores de um time")
    public ResponseEntity<List<JogadorResumoDTO>> listarJogadores(@PathVariable Long id) {
        return ResponseEntity.ok(timeService.listarJogadoresDoTime(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo time")
    public ResponseEntity<TimeResponseDTO> criar(@RequestBody @Valid TimeRequestDTO dto) {
        TimeResponseDTO criado = timeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um time existente")
    public ResponseEntity<TimeResponseDTO> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid TimeRequestDTO dto) {
        return ResponseEntity.ok(timeService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um time")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        timeService.deletar(id);
        return ResponseEntity.noContent().build();
    }



}

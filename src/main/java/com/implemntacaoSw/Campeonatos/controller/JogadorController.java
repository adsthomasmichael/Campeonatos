package com.implemntacaoSw.Campeonatos.controller;

import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorRequestDTO;
import com.implemntacaoSw.Campeonatos.dto.jogador.JogadorResponseDTO;
import com.implemntacaoSw.Campeonatos.entity.Jogador;
import com.implemntacaoSw.Campeonatos.service.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
@Tag(name = "Jogadores", description = "Gerenciamento de jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService){ this.jogadorService = jogadorService;}

    @GetMapping
    @Operation(summary = "Busca um jogados pelo id")
    public ResponseEntity<List<JogadorResponseDTO>> listarTodos(){
        return ResponseEntity.ok(jogadorService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um jogador pelo id")
    public ResponseEntity<JogadorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jogadorService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo jogador")
    public ResponseEntity<JogadorResponseDTO> criar(@RequestBody @Valid JogadorRequestDTO dto) {
        JogadorResponseDTO criado = jogadorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um jogador existente")
    public ResponseEntity<JogadorResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid JogadorRequestDTO dto) {
        return ResponseEntity.ok(jogadorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um jogador")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        jogadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}

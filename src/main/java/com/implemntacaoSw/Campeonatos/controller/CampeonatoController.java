package com.implemntacaoSw.Campeonatos.controller;

import com.implemntacaoSw.Campeonatos.dto.campeonato.CampeonatoRequestDTO;
import com.implemntacaoSw.Campeonatos.dto.campeonato.CampeonatoResponseDTO;
import com.implemntacaoSw.Campeonatos.service.CampeonatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
@Tag(name = "Campeonatos", description = "Gerenciamento de campeonatos esportivos")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;


    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os campeonatos")
    public ResponseEntity<List<CampeonatoResponseDTO>> listarTodos(){
        return ResponseEntity.ok(campeonatoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca campeonato pelo id")
    public ResponseEntity<CampeonatoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(campeonatoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo campeonato")
    public ResponseEntity<CampeonatoResponseDTO> criar(@RequestBody @Valid CampeonatoRequestDTO dto) {
        CampeonatoResponseDTO criado = campeonatoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping ("/{id}")
    @Operation(summary = "Atualiza um campeonato existente")
    public ResponseEntity<CampeonatoResponseDTO> atualizar(@PathVariable Long id,
                                                           @RequestBody @Valid CampeonatoRequestDTO dto){
        return ResponseEntity.ok(campeonatoService.atualizar(id,dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um campeonato")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        campeonatoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{campeonatoId}/times/{timeId}")
    @Operation(summary = "Associa um time a um campeonato (relação N:N)")
    public ResponseEntity<CampeonatoResponseDTO> adicionarTime(@PathVariable Long campeonatoId,
                                                               @PathVariable Long timeId) {
        return ResponseEntity.ok(campeonatoService.adicionarTime(campeonatoId, timeId));
    }

    @DeleteMapping("/{campeonatoId}/times/{timeId}")
    @Operation(summary = "Remove a associação entre um time e um campeonato")
    public ResponseEntity<CampeonatoResponseDTO> removerTime(@PathVariable Long campeonatoId,
                                                             @PathVariable Long timeId) {
        return ResponseEntity.ok(campeonatoService.removerTime(campeonatoId, timeId));
    }


}

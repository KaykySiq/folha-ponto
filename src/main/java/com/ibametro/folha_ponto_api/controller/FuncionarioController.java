package com.ibametro.folha_ponto_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibametro.folha_ponto_api.dtos.funcionarioDTO.FuncionarioRequest;
import com.ibametro.folha_ponto_api.services.FuncionarioService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<?> createFuncionario(@Valid @RequestBody FuncionarioRequest request) {
        funcionarioService.cadastrarFuncionario(request.name(), request.employeeId());
        return ResponseEntity.ok().body("Funcionário cadastrado com sucesso.");
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<?> putFuncionario(@PathVariable Long id, @Valid @RequestBody FuncionarioRequest request) {
        funcionarioService.atualizarFuncionario(id, request.name(), request.employeeId());
        return ResponseEntity.ok().body("Funcionário atualizado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncionario (@PathVariable Long id) {
        funcionarioService.removerFuncionario(id);
        return ResponseEntity.ok().body("Funcionário removido com sucesso.");
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getFuncionario() {
        return ResponseEntity.ok().body(funcionarioService.getFuncionarios());
    }
    
}

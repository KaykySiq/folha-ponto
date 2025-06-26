package com.ibametro.folha_ponto.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ibametro.folha_ponto.domain.Funcionario;
import com.ibametro.folha_ponto.domain.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void cadastrarFuncionario(String name, String employeeId) {
        if (name == null || name.isBlank() || employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException("Nome e ID do funcionário não podem ser nulos ou vazios.");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setName(name);
        funcionario.setEmployeeId(employeeId);

        funcionarioRepository.save(funcionario);
    }

    public void removerFuncionario(Long id) {
        if (id == null || !funcionarioRepository.existsById(id)) {
            throw new IllegalArgumentException("ID do funcionário não pode ser nulo.");
        }

        funcionarioRepository.deleteById(id);
    }

    public void atualizarFuncionario(Long id, String name, String employeeId) {
        if (id == null || name == null || name.isBlank() || employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException("ID, nome e matrícula não podem ser nulos ou vazios.");
        }

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isEmpty()) {
            throw new IllegalArgumentException("Funcionário com ID " + id + " não encontrado.");
        }

        Funcionario funcionario = optionalFuncionario.get();
        funcionario.setName(name);
        funcionario.setEmployeeId(employeeId);

        funcionarioRepository.save(funcionario);
    }

}

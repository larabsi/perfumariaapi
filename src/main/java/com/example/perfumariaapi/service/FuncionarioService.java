package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Funcionario;
import com.example.perfumariaapi.model.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService {
    private static FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository){this.repository = funcionarioRepository;}
    public List<Funcionario> getFuncionario(){ return repository.findAll();}

    public Optional<Funcionario> getFuncionarioById(Long id){ return repository.findById(id); }

    @Transactional
    public Funcionario salvar(Funcionario funcionario){
        validar(funcionario);
        return repository.save(funcionario);

    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }
    public void validar(Funcionario funcionario) {
        if (funcionario.getCpf()== null|| funcionario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("Cpf inválido");
        }
        if (funcionario.getNome()== null|| funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (funcionario.getNumeroTelefone()== null|| funcionario.getNumeroTelefone().trim().equals("")) {
            throw new RegraNegocioException("Número de telefone inválido");
        }
        if (funcionario.getSalario()== null) {
            throw new RegraNegocioException("Salário inválido");
        }
        if (funcionario.getCargo()== null ) {
            throw new RegraNegocioException("Cargo inválido");
        }

    }

}

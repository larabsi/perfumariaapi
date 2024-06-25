package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Funcionario;
import com.example.perfumariaapi.model.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void validar(Funcionario funcionario) {
        if (funcionario.getCpf()==null|| funcionario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("Funcionário inválido");
        }


    }

}

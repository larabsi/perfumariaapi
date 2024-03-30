package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Funcionario;
import com.example.perfumariaapi.model.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository){this.repository = repository;}
    public List<Funcionario> getFuncionario(){ return repository.findAll();}

    public static Optional<Funcionario> getFuncionarioById(Long id){ return repository.findById(id); }


}

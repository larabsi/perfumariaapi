package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.repository.FornecedorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    private static FornecedorRepository repository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.repository = repository;
    }

    public List<Fornecedor> getFornecedor() {
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return repository.findById(id);
    }


}
package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.repository.FornecedorRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    private static FornecedorRepository repository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.repository = fornecedorRepository;
    }

    public List<Fornecedor> getFornecedor() {
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor){
        validar(fornecedor);
        return repository.save(fornecedor);

    }
    public void validar(Fornecedor fornecedor) {
        if (fornecedor.getCnpj()==null) {
            throw new RegraNegocioException("Fornecedor inválido");
        }


    }
}
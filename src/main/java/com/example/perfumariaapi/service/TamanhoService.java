package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.model.repository.FornecedorRepository;

import com.example.perfumariaapi.model.repository.TamanhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TamanhoService {
    private TamanhoRepository repository;

    public TamanhoService(FornecedorRepository tamanhoRepository) {
        this.repository = repository;
    }

    public List<Tamanho> getTamanho() {
        return repository.findAll();
    }

    public Optional<Tamanho> getTamanhoById(Long id) {
        return repository.findById(id);
    }


}
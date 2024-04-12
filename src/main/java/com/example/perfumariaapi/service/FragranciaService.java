package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FragranciaService {
    private FragranciaRepository repository;

    public FragranciaService(FragranciaRepository fragranciaRepository) {
        this.repository = fragranciaRepository;
    }

    public List<Fragrancia> getFragrancia() {
        return repository.findAll();
    }

    public Optional<Fragrancia> getFragranciaById(Long id) {
        return repository.findById(id);
    }


}
package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Perda;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import com.example.perfumariaapi.model.repository.PerdaRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerdaService {
    private PerdaRepository repository;

    public PerdaService(PerdaRepository perdaRepository) {
        this.repository = perdaRepository;
    }

    public List<Perda> getPerda() {
        return repository.findAll();
    }

    public Optional<Perda> getPerdaById(Long id) {
        return repository.findById(id);
    }
}
package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cupom;
import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Fragrancia salvar(Fragrancia fragrancia){
        validar(fragrancia);
        return repository.save(fragrancia);

    }
    public void validar(Fragrancia fragrancia) {
        if (fragrancia.getDescricao() == null ) {
            throw new RegraNegocioException("Fragrancia inválida");
        }


    }

}
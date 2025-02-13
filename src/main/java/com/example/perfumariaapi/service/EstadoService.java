package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.model.entity.Estado;

import com.example.perfumariaapi.model.repository.EstadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service

public class EstadoService {
    private final EstadoRepository repository;

    public EstadoService(EstadoRepository repository)
    {this.repository = repository;
    }
    public List<Estado> getEstados(){
        return repository.findAll();
    }

    public Optional<Estado> getEstadoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Estado salvar(Estado estado) {
        validar(estado);
        return repository.save(estado);
    }
    @Transactional
    public void excluir(Estado estado) {
        Objects.requireNonNull(estado.getId());
        repository.delete(estado);
    }
    public void validar(Estado estado) {
        if (estado.getNome() == null || estado.getNome().trim().equals("")) {
            throw new RegraNegocioException("cargo inválido");
        }


    }
}

package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Perda;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import com.example.perfumariaapi.model.repository.PerdaRepository;
import javax.persistence.*;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Perda salvar(Perda perda){
        validar(perda);
        return repository.save(perda);
    }

    @Transactional
    public void excluir(Perda perda) {
        Objects.requireNonNull(perda.getId());
        repository.delete(perda);
    }
    public void validar(Perda perda) {
        if ( perda.getProduto()==null) {
            throw new RegraNegocioException("Produto inválido");
        }
        if (perda.getData()==null|| perda.getData().trim().equals("")) {
            throw new RegraNegocioException("Data inválida");
        }
        if (perda.getCodigoBarras()==null|| perda.getCodigoBarras().trim().equals("")) {
            throw new RegraNegocioException("Codigo de barras inválido");
        }


    }
}
package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FragranciaService {
    private FragranciaRepository repository;
    public FragranciaService(FragranciaRepository repository) {
        this.repository = repository;
    }
    public List<Fragrancia> getFragrancias() {
        return repository.findAll();
    }
    public Optional<Fragrancia> getFragranciaById(Long id) {
        return repository.findById(id);
    }

    public List<Fragrancia> getCFragranciasByProduto(Optional<Produto> produtos) {
        return repository.findByProdutos(produtos);
    }

    @Transactional
    public Fragrancia salvar(Fragrancia fragrancia){
        validar(fragrancia);
        return repository.save(fragrancia);

    }

    @Transactional
    public void excluir(Fragrancia fragrancia) {
        Objects.requireNonNull(fragrancia.getId());
        repository.delete(fragrancia);
    }
    public void validar(Fragrancia fragrancia) {
        if (fragrancia.getDescricao() == null ||fragrancia.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Fragrancia inválida");
        }


    }

}
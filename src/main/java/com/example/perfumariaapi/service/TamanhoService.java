package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.model.repository.FornecedorRepository;

import com.example.perfumariaapi.model.repository.TamanhoRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TamanhoService {
    private final TamanhoRepository repository;

    public TamanhoService(TamanhoRepository repository) {
        this.repository = repository;
    }

    public List<Tamanho> getTamanhos() {
        return repository.findAll();
    }

    public Optional<Tamanho> getTamanhoById(Long id) { return repository.findById(id); }

    public List<Tamanho> getTamanhoByProduto(Optional<Produto> produtos) {
        return repository.findByProdutos(produtos);
    }

    @Transactional
    public Tamanho salvar(Tamanho tamanho){
        validar(tamanho);
        return repository.save(tamanho);
    }

    @Transactional
    public void excluir(Tamanho tamanho) {
        Objects.requireNonNull(tamanho.getId());
        repository.delete(tamanho);
    }

    public void validar(Tamanho tamanho) {
        if (tamanho.getVolume()==null ||tamanho.getVolume().trim().equals("")) {
            throw new RegraNegocioException("Tamanho inválido");
        }
    }
}
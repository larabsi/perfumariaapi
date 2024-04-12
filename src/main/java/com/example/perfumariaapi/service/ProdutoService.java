package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import com.example.perfumariaapi.model.repository.ProdutoRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static ProdutoRepository repository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.repository = produtoRepository;
    }

    public List<Produto> getProduto() {
        return repository.findAll();
    }

    public Optional<Produto> getProdutoById(Long id) {
        return repository.findById(id);
    }


   /* public List<Produto> getProdutosByClassificacao(Optional<Classificacao> classificacao) {
        return repository.findByClassificacao(classificacao);
    } */
}
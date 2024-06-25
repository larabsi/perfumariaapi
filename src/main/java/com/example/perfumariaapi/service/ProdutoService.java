package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;


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


    @Transactional
    public Produto salvar(Produto produto){
        validar(produto);
        return repository.save(produto);

    }
    public void validar(Produto produto) {
        if (produto.getNome()== null || produto.getNome().trim().equals("")) {
            throw new RegraNegocioException("Produto inválido");
        }


    }
   /* public List<Produto> getProdutosByClassificacao(Optional<Classificacao> classificacao) {
        return repository.findByClassificacao(classificacao);
    } */
}
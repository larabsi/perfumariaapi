package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
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
    public List<Produto> getProdutos() {
        return repository.findAll();
    }
    public Optional<Produto> getProdutoById(Long id) { return repository.findById(id); }
    public List<Produto> getProdutosByEstoque(Optional<Estoque> estoques) { return repository.findByEstoque(estoques); }
    public List<Produto> getProdutosByClassificacao(Optional<Classificacao> classificacao) { return repository.findByClassificacao(classificacao); }
    public List<Produto> getProdutosByFornecedor(Optional<Fornecedor> fornecedor) { return repository.findByFornecedor(fornecedor); }
    public List<Produto> getProdutosByFragrancia(Optional<Fragrancia> fragrancia) { return repository.findByFragrancia(fragrancia); }
    @Transactional
    public Produto salvar(Produto produto){
        validar(produto);
        return repository.save(produto);
    }
    public void validar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().equals("")) {
            throw new RegraNegocioException("Produto inválido");
        }
    }
}
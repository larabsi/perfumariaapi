package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.model.repository.ProdutoRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;




@Service
public class ProdutoService {
    private static ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        ProdutoService.repository = repository;
    }
    public List<Produto> getProdutos() {
        return repository.findAll();
    }
    public Optional<Produto> getProdutoById(Long id) { return repository.findById(id); }
    public List<Produto> getProdutosByFornecedor(Optional<Fornecedor> fornecedor) { return repository.findByFornecedor(fornecedor); }
    public List<Produto> getProdutosByFragrancia(Optional<Fragrancia> fragrancia) { return repository.findByFragrancia(fragrancia); }
    public List<Produto> getProdutosByTamanho(Optional<Tamanho> tamanho) { return repository.findByTamanho(tamanho); }
    public List<Produto> getProdutosByClassificacao(Optional<Classificacao> classificacao) { return repository.findByClassificacao(classificacao); }

    @Transactional
    public Produto salvar(Produto produto){
        validar(produto);
        return repository.save(produto);
    }

    @Transactional
    public void excluir(Produto produto) {
        Objects.requireNonNull(produto.getId());
        repository.delete(produto);
    }

    public void validar(Produto produto) {
        if (produto.getProduto() == null || produto.getProduto().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (produto.getClassificacao() == null ) {
            throw new RegraNegocioException("Classificacao inválida");
        }
        if (produto.getFragrancia() == null ) {
            throw new RegraNegocioException("Fragrancia inválida");
        }
        if (produto.getTamanho() == null) {
            throw new RegraNegocioException("Tamanho inválido");
        }
        if (produto.getCodigoBarras() == null) {
            throw new RegraNegocioException("Codigo de barras inválido");
        }


    }
}
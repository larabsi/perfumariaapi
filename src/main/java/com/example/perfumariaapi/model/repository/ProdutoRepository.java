package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByEstoque(Optional<Estoque> estoques);
    List<Produto> findByClassificacao(Optional<Classificacao> classificacao);
    List<Produto> findByFornecedor(Optional<Fornecedor> fornecedor);
    List<Produto> findByFragrancia(Optional<Fragrancia> fragrancia);
    List<Produto> findByTamanho(Optional<Tamanho> tamanho);



}
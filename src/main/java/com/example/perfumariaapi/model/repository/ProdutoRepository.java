package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
//    List<Produto> findByClassificacao(Optional<Classificacao> classificacao);
}

package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Tamanho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TamanhoRepository extends JpaRepository<Tamanho, Long> {
    List<Tamanho> findByProdutos(Optional<Produto> produtos);
}

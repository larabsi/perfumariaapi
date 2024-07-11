package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
public interface FragranciaRepository extends JpaRepository<Fragrancia, Long>{
    List<Fragrancia> findByProdutos(Optional<Produto> produtos);
}

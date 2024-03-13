package com.example.perfumariaapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.perfumariaapi.model.entity.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {


}

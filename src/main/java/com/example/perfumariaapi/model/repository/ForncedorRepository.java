package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;
public interface ForncedorRepository extends JpaRepository<Fornecedor, Long> {
}

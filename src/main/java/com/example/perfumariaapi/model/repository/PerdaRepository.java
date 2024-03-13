package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Perda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerdaRepository extends JpaRepository<Perda, Long> {
}

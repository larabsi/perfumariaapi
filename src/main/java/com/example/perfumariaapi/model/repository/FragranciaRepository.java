package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Fragrancia;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;
public interface FragranciaRepository extends JpaRepository<Fragrancia, Long>{
}

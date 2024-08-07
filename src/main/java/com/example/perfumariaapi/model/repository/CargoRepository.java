package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.model.entity.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}

package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;
public interface CupomRepository extends JpaRepository<Cupom, Long>{
}

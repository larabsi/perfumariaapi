package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ClienteRepository  extends JpaRepository<Cliente, Long>{
}

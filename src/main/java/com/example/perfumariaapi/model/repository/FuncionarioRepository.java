package com.example.perfumariaapi.model.repository;


import com.example.perfumariaapi.model.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}

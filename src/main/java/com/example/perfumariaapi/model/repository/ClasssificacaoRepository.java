package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface ClasssificacaoRepository  extends JpaRepository<Classificacao, Long> {
}



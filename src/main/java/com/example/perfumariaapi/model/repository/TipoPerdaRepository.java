package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.model.entity.TipoPerda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPerdaRepository extends JpaRepository<TipoPerda, Long> {
}

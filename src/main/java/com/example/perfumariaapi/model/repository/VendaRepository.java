package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {
//    List<Venda> findByCliente(Optional<Cliente> cliente);
}
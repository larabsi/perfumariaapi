package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Funcionario;
import com.example.perfumariaapi.model.entity.Item;
import com.example.perfumariaapi.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByCliente(Optional<Cliente> cliente);
    List<Venda> findByFuncionario(Optional<Funcionario> funcionario);
    List<Venda> findByItem(Optional<Item> item);
}
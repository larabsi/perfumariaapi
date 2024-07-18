package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
public interface PedidoRepository  extends JpaRepository<Pedido, Long> {

    List<Pedido> findByFornecedor(Optional<Fornecedor> fornecedor);
}

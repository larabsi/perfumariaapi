package com.example.perfumariaapi.model.repository;

import com.example.perfumariaapi.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;
public interface PedidoRepository  extends JpaRepository<Item, Long> {
}

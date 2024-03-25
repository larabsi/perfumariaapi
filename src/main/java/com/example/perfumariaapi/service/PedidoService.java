package com.example.perfumariaapi.service;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    public PedidoService(PedidoRepository pedidoRepository){this.repository = repository;}
    public List<Pedido> getPedido(){ return repository.findAll();}

    public Optional<Pedido> getPedidoById(Long id){ return repository.findById(id); }



}

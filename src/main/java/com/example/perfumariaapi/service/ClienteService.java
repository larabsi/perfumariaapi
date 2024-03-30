package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private ClienteRepository repository;

    public ClienteService(ClienteRepository clienteRepository){this.repository = repository;}
    public List<Cliente> getCliente(){ return repository.findAll();}

    public static Optional<Cliente> getClienteById(Long id){ return repository.findById(id); }


}

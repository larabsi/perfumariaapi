package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Estoque;
import com.example.perfumariaapi.model.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    private EstoqueRepository repository;

    public EstoqueService(EstoqueRepository estoqueRepository){this.repository = estoqueRepository;}
    public List<Estoque> getEstoque(){ return repository.findAll();}

    public Optional<Estoque> getEstoqueById(Long id){ return repository.findById(id); }


}
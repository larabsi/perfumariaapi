package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Cupom;
import com.example.perfumariaapi.model.repository.CupomRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CupomService {
    private CupomRepository repository;

    public CupomService(CupomRepository cupomRepository){this.repository = repository;}
    public List<Cupom> getCupom(){ return repository.findAll();}

    public Optional<Cupom> getCupomById(Long id){ return repository.findById(id); }




}
package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Cupom;
import com.example.perfumariaapi.model.repository.CupomRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CupomService {
    private CupomRepository repository;

    public CupomService(CupomRepository cupomRepository){this.repository = cupomRepository;}
    public List<Cupom> getCupom(){ return repository.findAll();}

    public Optional<Cupom> getCupomById(Long id){ return repository.findById(id); }

    @Transactional
    public Cupom salvar(Cupom cupom){
        validar(cupom);
        return repository.save(cupom);

    }
    public void validar(Cupom cupom) {
        if (cupom.getCodigo() == null || cupom.getCodigo().trim().equals("")) {
            throw new RegraNegocioException("Cupom inválido");
        }


    }


}
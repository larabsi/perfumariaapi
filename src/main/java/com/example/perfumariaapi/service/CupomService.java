package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.model.repository.CupomRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class CupomService {
    private final CupomRepository repository;

    public CupomService(CupomRepository repository)
    {this.repository = repository;
    }
    public List<Cupom> getCupons(){
        return repository.findAll();
    }

    public Optional<Cupom> getCupomById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cupom salvar(Cupom cupom){
        validar(cupom);
        return repository.save(cupom);

    }

    @Transactional
    public void excluir(Cupom cupom) {
        Objects.requireNonNull(cupom.getId());
        repository.delete(cupom);
    }
    public void validar(Cupom cupom) {
        if (cupom.getCodigo() == null || cupom.getCodigo().trim().equals("")) {
            throw new RegraNegocioException("Codigo inválido");
        }
        if (cupom.getDataExpiracao() == null ) {
            throw new RegraNegocioException("Data de Expiração inválida");
        }
        if (cupom.getDesconto()== null || cupom.getDesconto().trim().equals("")) {
            throw new RegraNegocioException("Desconto inválido");
        }

    }


}
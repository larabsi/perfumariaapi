package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.model.repository.FornecedorRepository;

import com.example.perfumariaapi.model.repository.TamanhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TamanhoService {
    private TamanhoRepository repository;

    public TamanhoService(TamanhoRepository tamanhoRepository) {
        this.repository = tamanhoRepository;
    }

    public List<Tamanho> getTamanho() {
        return repository.findAll();
    }

    public Optional<Tamanho> getTamanhoById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    public Tamanho salvar(Tamanho tamanho){
        validar(tamanho);
        return repository.save(tamanho);

    }
    public void validar(Tamanho tamanho) {
        if (tamanho.getProduto()==null) {
            throw new RegraNegocioException("Tamanho inválido");
        }


    }
}
package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Estoque;
import com.example.perfumariaapi.model.repository.EstoqueRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;




@Service
public class EstoqueService {

    private final EstoqueRepository repository;

    public EstoqueService(EstoqueRepository estoqueRepository){this.repository = estoqueRepository;}
    public List<Estoque> getEstoque(){ return repository.findAll();}

    public Optional<Estoque> getEstoqueById(Long id){ return repository.findById(id); }
    @Transactional
    public Estoque salvar(Estoque estoque){
        validar(estoque);
        return repository.save(estoque);
    }

    @Transactional
    public void excluir(Estoque estoque) {
        Objects.requireNonNull(estoque.getId());
        repository.delete(estoque);
    }
    public void validar(Estoque estoque) {
        if (estoque.getProduto()==null ) {
            throw new RegraNegocioException("Produto inválido");
        }
        if (estoque.getQuantidade() == null ) {
            throw new RegraNegocioException("Quantidade inválida");
        }
        if (estoque.getCapacidadeMaxima() == null ) {
            throw new RegraNegocioException("Capacidade Maxima inválida");
        }
        if (estoque.getCapacidadeMinima() == null ) {
            throw new RegraNegocioException("Capacidade minima inválida");
        }
        if (estoque.getPontoDeRessuprimento() ==null ) {
            throw new RegraNegocioException("Ponto de ressuprimento inválido");
        }
    }

}
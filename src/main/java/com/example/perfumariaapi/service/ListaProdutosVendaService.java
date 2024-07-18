package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.ListaProdutosVenda;
import com.example.perfumariaapi.model.repository.ListaProdutosVendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ListaProdutosVendaService {
    private ListaProdutosVendaRepository repository;

    public ListaProdutosVendaService(ListaProdutosVendaRepository repository) {
        this.repository = repository;
    }

    public List<ListaProdutosVenda>getListaProdutosVenda() {
        return repository.findAll();
    }

    public Optional<ListaProdutosVenda>getListaProdutosVendaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ListaProdutosVenda salvar(ListaProdutosVenda listaProdutosVenda){
        validar(listaProdutosVenda);
        return repository.save(listaProdutosVenda);
    }

    @Transactional
    public void excluir(ListaProdutosVenda listaProdutosVenda) {
        Objects.requireNonNull(listaProdutosVenda.getId());
        repository.delete(listaProdutosVenda);
    }

    public void validar(ListaProdutosVenda listaProdutosVenda) {
        if (listaProdutosVenda.getProduto() == null || listaProdutosVenda.getProduto().getNome().trim().equals("")) {
            throw new RegraNegocioException(" Produto Null");
        }
    }

}

package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.ListaPedido;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.repository.ListaPedidoRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ListaPedidoService {

    private final ListaPedidoRepository repository;

    public ListaPedidoService(ListaPedidoRepository repository){
        this.repository = repository;
    }

    public List<ListaPedido>getListaPedidos(){ return repository.findAll();
    }

    public Optional<ListaPedido>getListaPedidoById(Long id) {
        return repository.findById(id);
    }



    @Transactional
    public ListaPedido salvar(ListaPedido listaPedido){
        validar(listaPedido);
        return repository.save(listaPedido);
    }

    @Transactional
    public void excluir(ListaPedido listaPedido) {
        Objects.requireNonNull(listaPedido.getId());
        repository.delete(listaPedido);
    }

    public void validar(ListaPedido listaPedido) {
        if (listaPedido.getProduto() == null) {
          throw new RegraNegocioException(" Produto Null");
        }
    }


}


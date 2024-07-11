package com.example.perfumariaapi.service;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    public PedidoService(PedidoRepository pedidoRepository){this.repository = pedidoRepository;}
    public List<Pedido> getPedidos(){ return repository.findAll();}

    public Optional<Pedido> getPedidoById(Long id){ return repository.findById(id); }
    public List<Pedido> getPedidosByFornecedor(Optional<Fornecedor> fornecedor) {
        return repository.findByFornecedor(fornecedor);
    }

    @Transactional
    public Pedido salvar(Pedido pedido){
        validar(pedido);
        return repository.save(pedido);

    }

    @Transactional
    public void excluir(Pedido pedido) {
        Objects.requireNonNull(pedido.getId());
        repository.delete(pedido);
    }

    public void validar(Pedido pedido) {
        if (pedido.getFornecedor() == null) {
            throw new RegraNegocioException("Forncedor inválido");
        }
        if (pedido.getDataPedido() == null|| pedido.getDataPedido().trim().equals("")) {
            throw new RegraNegocioException("Data inválida");
        }
        if (pedido.getValor() == null|| pedido.getValor().trim().equals("")) {
            throw new RegraNegocioException("Valor inválido");
        }
        if (pedido.getProduto() == null) {
            throw new RegraNegocioException("Produto inválido");
        }
    }

}

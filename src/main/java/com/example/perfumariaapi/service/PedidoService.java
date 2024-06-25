package com.example.perfumariaapi.service;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    public PedidoService(PedidoRepository pedidoRepository){this.repository = pedidoRepository;}
    public List<Pedido> getPedido(){ return repository.findAll();}

    public Optional<Pedido> getPedidoById(Long id){ return repository.findById(id); }

    @Transactional
    public Pedido salvar(Pedido pedido){
        validar(pedido);
        return repository.save(pedido);

    }
    public void validar(Pedido pedido) {
        if (pedido.getFornecedor()==null) {
            throw new RegraNegocioException("Pedido inválido");
        }


    }

}

package com.example.perfumariaapi.service;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.model.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendaService {
    private static VendaRepository repository;

    public VendaService(VendaRepository vendaRepository) { this.repository = vendaRepository;}
    public List<Venda> getVendas(){ return repository.findAll();}
    public Optional<Venda> getVendaById(Long id){ return repository.findById(id); }
    public List<Venda> getVendasByCliente(Optional<Cliente> cliente) { return repository.findByCliente(cliente); }
    public List<Venda> getVendasByFuncionario(Optional<Funcionario> funcionario) { return repository.findByFuncionario(funcionario); }
    public List<Venda> getVendasByListaProdutosVenda(Optional<ListaProdutosVenda> listaProdutosVenda) { return repository.findByListaProdutosVenda(listaProdutosVenda); }

    @Transactional
    public Venda salvar(Venda venda){
        validar(venda);
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda) {
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }

    public void validar(Venda venda) {
        if (venda.getCliente() == null) {
            throw new RegraNegocioException("Venda inválida. Venda sem cliente");
        }

        if (venda.getFuncionario() == null) {
            throw new RegraNegocioException("Funcionário inválido");
        }
        if (venda.getValor_total() == null) {
            throw new RegraNegocioException("Valor inválido");
        }
        if (venda.getFormaPagamento() == null) {
            throw new RegraNegocioException("Forma de Pagamento inválida");
        }
        if (venda.getListaProdutosVenda() == null) {
            throw new RegraNegocioException("Lista de produto Inválida");
        }
    }

}
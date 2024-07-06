package com.example.perfumariaapi.service;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.model.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    private static VendaRepository repository;

    public VendaService(VendaRepository vendaRepository) { this.repository = vendaRepository;}
    public List<Venda> getVendas(){ return repository.findAll();}
    public Optional<Venda> getVendaById(Long id){ return repository.findById(id); }
    public List<Venda> getVendasByCliente(Optional<Cliente> cliente) { return repository.findByCliente(cliente); }
    public List<Venda> getVendasByFuncionario(Optional<Funcionario> funcionario) { return repository.findByFuncionario(funcionario); }
    public List<Venda> getVendasByItem(Optional<Item> item) { return repository.findByItem(item); }

    @Transactional
    public Venda salvar(Venda venda){
        validar(venda);
        return repository.save(venda);
    }

    public void validar(Venda venda) {
        if (venda.getCliente() == null) {
            throw new RegraNegocioException("Venda inválida");
        }
    }

}
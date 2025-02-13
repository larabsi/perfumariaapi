package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.repository.FornecedorRepository;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FornecedorService {
    private static FornecedorRepository repository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        repository = fornecedorRepository;
    }

    public List<Fornecedor> getFornecedores() {
        return repository.findAll();
    }

    public Optional<Fornecedor> getFornecedorById(Long id) {
        return repository.findById(id);
    }

    public List<Fornecedor> getFornecedoresByPedido(Optional<Pedido> pedido) {
        return repository.findByPedido(pedido);
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor){
        validar(fornecedor);
        return repository.save(fornecedor);

    }

    @Transactional
    public void excluir(Fornecedor fornecedor) {
        Objects.requireNonNull(fornecedor.getId());
        repository.delete(fornecedor);
    }
    public void validar(Fornecedor fornecedor) {
        if (fornecedor.getCnpj()==null) {
            throw new RegraNegocioException("Cnpj inválido");
        }
        if (fornecedor.getNome()==null|| fornecedor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (fornecedor.getNumeroTelefone()==null|| fornecedor.getNumeroTelefone().trim().equals("")) {
            throw new RegraNegocioException("Número de telefone inválido");
        }
        if (fornecedor.getEmail()==null|| fornecedor.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
    }
}
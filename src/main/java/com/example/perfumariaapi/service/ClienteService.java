package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Cupom;
import com.example.perfumariaapi.model.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private static ClienteRepository repository;

    public ClienteService(ClienteRepository clienteRepository){this.repository = clienteRepository;}
    public List<Cliente> getCliente(){ return repository.findAll();}

    public Optional<Cliente> getClienteById(Long id){ return repository.findById(id); }

    @Transactional
    public Cliente salvar(Cliente cliente){
        validar(cliente);
        return repository.save(cliente);
    }
    public void validar(Cliente cliente) {
        if (cliente.getCpf()== null|| cliente.getCpf().trim().equals("")) {
            throw new RegraNegocioException("Cliente inválido");
        }
    }

}

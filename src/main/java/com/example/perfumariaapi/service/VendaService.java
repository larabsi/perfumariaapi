package com.example.perfumariaapi.service;
import com.example.perfumariaapi.model.entity.Venda;
import com.example.perfumariaapi.model.repository.VendaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private VendaRepository repository;

    public VendaService(VendaRepository vendaRepository) { this.repository = repository;}
    public List<Venda> getVendas(){ return repository.findAll();}

    public static Optional<Venda> getVendaById(Long id){ return repository.findById(id); }


}

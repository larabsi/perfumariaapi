package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.ClienteService;
import com.example.perfumariaapi.service.FuncionarioService;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
@CrossOrigin

public class VendaController {
    private final VendaService service;
    private final ProdutoService produtoService;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(VendaDTO dto) {
        try {
            Venda venda = converter(dto);
            venda = service.salvar(venda);
            return new ResponseEntity(venda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                venda.setProduto(null);
            } else{
                venda.setProduto(produto.get());
            }
        }
        if(dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if(!cliente.isPresent()){
                venda.setCliente(null);
            }else {
                venda.setCliente(cliente.get());
            }
        }
        if(dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                venda.setProduto(null);
            } else {
                venda.setFuncionario(funcionario.get());
            }
        }
        return venda;
    }
}

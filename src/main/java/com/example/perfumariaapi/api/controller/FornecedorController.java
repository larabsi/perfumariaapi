package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.FornecedorDTO;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.FornecedorService;
import com.example.perfumariaapi.service.PedidoService;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
@CrossOrigin

public class FornecedorController {
    private final FornecedorService service;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @GetMapping("{id}/produtos")
    public ResponseEntity getProdutos(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não vende produto", HttpStatus.NOT_FOUND);
        }
        List<Produto> produtos = produtoService.getProdutosByFornecedor(fornecedor);
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("{id}/pedidos")
    public ResponseEntity getPedidos(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Não existe esse pedido feito pelo fornecedor", HttpStatus.NOT_FOUND);
        }
        List<Pedido> pedidos = pedidoService.getPedidosByFornecedor(fornecedor);
        return ResponseEntity.ok(pedidos.stream().map(PedidoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post( @RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor = service.salvar(fornecedor);
            return new ResponseEntity(fornecedor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FornecedorDTO dto) {
        if (!service.getFornecedorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor.setId(id);
            service.salvar(fornecedor);
            return ResponseEntity.ok(fornecedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                fornecedor.setProduto(null);
            } else{
                fornecedor.setProduto(produto.get());
            }
        }
        return fornecedor;
    }
}
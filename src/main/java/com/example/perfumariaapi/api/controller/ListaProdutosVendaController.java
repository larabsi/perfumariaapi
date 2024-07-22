package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ListaProdutosVendaDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.ListaProdutosVendaService;
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
@RequestMapping("/api/v1/listaProdutosVendas")
@RequiredArgsConstructor
@CrossOrigin

public class ListaProdutosVendaController {
    private final ListaProdutosVendaService service;
    private final VendaService vendaService;
    private final ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity get() {
        List<ListaProdutosVenda> listaProdutosVendas = service.getListaProdutosVenda();
        return ResponseEntity.ok(listaProdutosVendas.stream().map(ListaProdutosVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ListaProdutosVenda>listaProdutosVenda = service.getListaProdutosVendaById(id);
        if (!listaProdutosVenda.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaProdutosVenda.map(ListaProdutosVendaDTO::create));
    }

    @GetMapping("{id}/vendas")
    public ResponseEntity getVendas(@PathVariable("id") Long id) {
        Optional<ListaProdutosVenda> listaProdutosVenda = service.getListaProdutosVendaById(id);
        if (!listaProdutosVenda.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Venda> vendas = vendaService.getVendasByListaProdutosVenda(listaProdutosVenda);
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ListaProdutosVendaDTO dto) {
        try {
            ListaProdutosVenda listaProdutosVenda = converter(dto);
            listaProdutosVenda = service.salvar(listaProdutosVenda);
            return new ResponseEntity(listaProdutosVenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ListaProdutosVendaDTO dto) {
        if (!service.getListaProdutosVendaById(id).isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ListaProdutosVenda listaProdutosVenda = converter(dto);
            listaProdutosVenda.setId(id);
            service.salvar(listaProdutosVenda);
            return ResponseEntity.ok(listaProdutosVenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ListaProdutosVenda> listaProdutosVenda = service.getListaProdutosVendaById(id);
        if (!listaProdutosVenda.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(listaProdutosVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ListaProdutosVenda converter(ListaProdutosVendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ListaProdutosVenda listaProdutosVenda = modelMapper.map(dto, ListaProdutosVenda.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                listaProdutosVenda.setProduto(null);
            } else{
                listaProdutosVenda.setProduto(produto.get());
            }

        }
        return listaProdutosVenda;
    }

}

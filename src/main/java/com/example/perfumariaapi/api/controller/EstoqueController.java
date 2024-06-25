package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.api.dto.EstoqueDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Estoque;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Venda;
import com.example.perfumariaapi.service.EstoqueService;
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
@RequestMapping("/api/v1/estoques")
@RequiredArgsConstructor
@CrossOrigin

public class EstoqueController {
    private final EstoqueService service;
    private final ProdutoService produtoService;
    @GetMapping()
    public ResponseEntity get() {
        List<Estoque> estoques = service.getEstoque();
        return ResponseEntity.ok(estoques.stream().map(EstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estoque> estoque = service.getEstoqueById(id);
        if (!estoque.isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estoque.map(EstoqueDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(EstoqueDTO dto) {
        try {
            Estoque estoque = converter(dto);
            estoque = service.salvar(estoque);
            return new ResponseEntity(estoque, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Estoque converter(EstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estoque estoque = modelMapper.map(dto, Estoque.class);

        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                estoque.setProduto(null);
            } else{ estoque.setProduto(produto.get());} }


        return estoque;
    }
}

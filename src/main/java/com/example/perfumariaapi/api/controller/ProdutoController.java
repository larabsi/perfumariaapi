package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ProdutoService;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@CrossOrigin
public class ProdutoController {
    private final ProdutoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Produto> produtos = service.getProdutos();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(@RequestBody ProdutoDTO dto) {
        try {
            Produto produto = converter(dto);
            produto = service.salvar(produto);
            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);

        return produto;
    }
}
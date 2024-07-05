package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.api.dto.TamanhoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.TamanhoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/tamanhos")
@RequiredArgsConstructor
@CrossOrigin

public class TamanhoController {
    private final TamanhoService service;
    private final ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Tamanho> tamanhos = service.getTamanho();
        return ResponseEntity.ok(tamanhos.stream().map(TamanhoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Tamanho> tamanho = service.getTamanhoById(id);
        if (!tamanho.isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tamanho.map(TamanhoDTO::create));
    }
    @PostMapping()
    public ResponseEntity post( @RequestBody TamanhoDTO dto) {
        try {
            Tamanho tamanho = converter(dto);
            tamanho = service.salvar(tamanho);
            return new ResponseEntity(tamanho, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Tamanho converter(TamanhoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Tamanho tamanho = modelMapper.map(dto, Tamanho.class);

        if(dto.getIdProduto() != null) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                tamanho.setProduto(null);
            } else{ tamanho.setProduto(produto.get());} }

        return tamanho;
    }
}

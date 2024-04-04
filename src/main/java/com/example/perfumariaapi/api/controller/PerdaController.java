package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.PerdaDTO;
import com.example.perfumariaapi.model.entity.Perda;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.PerdaService;
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
@RequestMapping("/api/v1/perdas")
@RequiredArgsConstructor
@CrossOrigin

public class PerdaController {
    private final PerdaService service;
    private final ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Perda> perdas = service.getPerda();
        return ResponseEntity.ok(perdas.stream().map(PerdaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Perda> perda = service.getPerdaById(id);
        if (!perda.isPresent()) {
            return new ResponseEntity("Perda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(perda.map(PerdaDTO::create));
    }
    public Perda converter(PerdaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Perda perda = modelMapper.map(dto, Perda.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                perda.setProduto(null);
            } else{ perda.setProduto(produto.get());} }

        return perda;
    }
}

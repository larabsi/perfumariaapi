package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.repository.ClassificacaoRepository;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ClassificacaoService;
import  com.example.perfumariaapi.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.perfumariaapi.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/classificacoes")
@RequiredArgsConstructor
@CrossOrigin
public class ClassificacaoController {
    private final ProdutoService produtoService;
    private final ClassificacaoService service;

    @GetMapping
    public ResponseEntity get() {
        List<Classificacao> classificacoes = service.getClassificacao();
        return ResponseEntity.ok(classificacoes.stream().map(ClassificacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Classificacao> classificacao = service.getClassificacaoById(id);
        if (!classificacao.isPresent()) {
            return new ResponseEntity("Classificacao não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(classificacao.map(ClassificacaoDTO::create));
    }
//   @GetMapping("{id}/produtos")
//    public ResponseEntity getProdutos(@PathVariable("id") Long id) {
//        Optional<Classificacao> classificacao = service.getClassificacaoById(id);
//        if (!classificacao.isPresent()) {
//            return new ResponseEntity("Classificacao não encontrada", HttpStatus.NOT_FOUND);
//        }
//        List<Produto> produtos = produtoService.getClassificacaoById(classificacao);
//        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
//    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ClassificacaoDTO dto) {
        try {
            Classificacao classificacao = converter(dto);
            classificacao = service.salvar(classificacao);
            return new ResponseEntity(classificacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Classificacao converter(  ClassificacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Classificacao classificacao = modelMapper.map(dto, Classificacao.class);
        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                classificacao.setProduto(null);
            } else{ classificacao.setProduto(produto.get());} }

        return classificacao;

    }
}
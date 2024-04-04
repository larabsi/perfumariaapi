package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ClassificacaoService;
import  com.example.perfumariaapi.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

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
    // private final ProdutoService produtoService;
    private final ClassificacaoService service;

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
   /* @GetMapping("{id}/produtos")
    public ResponseEntity getProdutos(@PathVariable("id") Long id) {
        Optional<Classificacao> classificacao = service.getClassificacaoById(id);
        if (!classificacao.isPresent()) {
            return new ResponseEntity("Classificacao não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Produto> produtos = produtoService.getProdutosByClassificacao(classificacao);
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }*/

    public Classificacao converter(ClassificacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Classificacao classificacao = modelMapper.map(dto, Classificacao.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto=ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                classificacao.setProduto(null);
            } else{ classificacao.setProduto(produto.get());} }

        return classificacao;

    }
}

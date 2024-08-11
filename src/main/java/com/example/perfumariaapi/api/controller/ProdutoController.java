package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.api.dto.FragranciaDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.api.dto.TamanhoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.*;
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
    private final TamanhoService tamanhoService;
    private final ClassificacaoService classificacaoService;
    private final FragranciaService fragranciaService;
    private final EstoqueService estoqueService;

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

    @GetMapping("{id}/tamanhos")
    public ResponseEntity getTamanhos(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Tamanho nao encontrado", HttpStatus.NOT_FOUND);
        }
        List<Tamanho> tamanhos = tamanhoService.getTamanhoByProduto(produto);
        return ResponseEntity.ok(tamanhos.stream().map(TamanhoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("{id}/classificacoes")
    public ResponseEntity getClassificacoes(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Classificação não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Classificacao> classificacao = classificacaoService.getClassificacoesByProduto(produto);
        return ResponseEntity.ok(classificacao.stream().map(ClassificacaoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("{id}/fragrancias")
    public ResponseEntity getFragrancias(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Fragrancia não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Fragrancia> fragrancias = fragranciaService.getCFragranciasByProduto(produto);
        return ResponseEntity.ok(fragrancias.stream().map(FragranciaDTO::create).collect(Collectors.toList()));
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

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
        if (!service.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produto produto = converter(dto);
            produto.setId(id);
            service.salvar(produto);
            return ResponseEntity.ok(produto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (!produto.isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        if(dto.getIdClassificacao() != null) {
            Optional<Classificacao> classificacao = classificacaoService.getClassificacaoById(dto.getIdClassificacao());
            if(!classificacao.isPresent()){
                produto.setClassificacao(null);
            }else {
                produto.setClassificacao(classificacao.get());
            }
        }
        if(dto.getIdFragrancia() != null) {
            Optional<Fragrancia> fragrancia = fragranciaService.getFragranciaById(dto.getIdFragrancia());
            if(!fragrancia.isPresent()){
                produto.setFragrancia(null);
            }else {
                produto.setFragrancia(fragrancia.get());
            }
        }
        if(dto.getIdTamanho() != null) {
            Optional<Tamanho> tamanho = tamanhoService.getTamanhoById(dto.getIdTamanho());
            if(!tamanho.isPresent()){
                produto.setTamanho(null);
            }else {
                produto.setTamanho(tamanho.get());
            }
        }
        return produto;
    }

}
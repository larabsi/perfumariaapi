package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.api.dto.FornecedorDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Fragrancia;
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
    private final ClassificacaoService service;
    private final ProdutoService produtoService;
    @GetMapping
    public ResponseEntity get() {
        List<Classificacao> classificacoes = service.getClassificacoes();
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
    @GetMapping("{id}/produtos")
    public ResponseEntity getProdutos(@PathVariable("id") Long id) {
        Optional<Classificacao> classificacao = service.getClassificacaoById(id);
        if (!classificacao.isPresent()) {
            return new ResponseEntity("Não existe produto com essa classificação", HttpStatus.NOT_FOUND);
        }
        List<Produto> produtos = produtoService.getProdutosByClassificacao(classificacao);
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

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

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClassificacaoDTO dto) {
        if (!service.getClassificacaoById(id).isPresent()) {
            return new ResponseEntity("Classificação não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Classificacao classificacao = converter(dto);
            classificacao.setId(id);
            service.salvar(classificacao);
            return ResponseEntity.ok(classificacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Classificacao> classificacao = service.getClassificacaoById(id);
        if (!classificacao.isPresent()) {
            return new ResponseEntity("Classificacao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(classificacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Classificacao converter(  ClassificacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Classificacao.class);
    }
}
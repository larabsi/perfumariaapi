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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Vizualizar uma classificação" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação encontrada"),
            @ApiResponse(code = 404, message = "Classificação não encontrada")
    })
    public ResponseEntity get() {
        List<Classificacao> classificacoes = service.getClassificacoes();
        return ResponseEntity.ok(classificacoes.stream().map(ClassificacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma classificação" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação encontrada"),
            @ApiResponse(code = 404, message = "Classificação não encontrada")
    })
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
    @ApiOperation("Salvar uma nova classificação" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar  Classificação")
    })
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
    @ApiOperation("Atualizar classificação" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar a Classificação")
    })
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
    @ApiOperation("Deletar classificação" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Classificação excluida cm sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Classificação")
    })
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
package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.*;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.EstoqueService;
import com.example.perfumariaapi.service.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Visualizar Estoque" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estoque encontrado"),
            @ApiResponse(code = 404, message = "Estoque não encontrado")
    })
    public ResponseEntity get() {
        List<Estoque> estoques = service.getEstoque();
        return ResponseEntity.ok(estoques.stream().map(EstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes do Estoque" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estoque encontrado"),
            @ApiResponse(code = 404, message = "Estoque não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estoque> estoque = service.getEstoqueById(id);
        if (!estoque.isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estoque.map(EstoqueDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo estoque" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estoque salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro salvar o Estoque")
    })
    public ResponseEntity post(@RequestBody EstoqueDTO dto) {
        try {
            Estoque estoque = converter(dto);
            estoque = service.salvar(estoque);
            return new ResponseEntity(estoque, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar Estoque" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Atualizar estoque"),
            @ApiResponse(code = 404, message = "Erro ao atualizar estoque")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EstoqueDTO dto) {
        if (!service.getEstoqueById(id).isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Estoque estoque = converter(dto);
            estoque.setId(id);
            service.salvar(estoque);
            return ResponseEntity.ok(estoque);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar Estoque" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estoque excluido com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Estoque")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Estoque> estoque = service.getEstoqueById(id);
        if (!estoque.isPresent()) {
            return new ResponseEntity("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(estoque.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Estoque converter(EstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estoque estoque = modelMapper.map(dto, Estoque.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                estoque.setProduto(null);
            } else{ estoque.setProduto(produto.get());} }
        return estoque;
    }

}
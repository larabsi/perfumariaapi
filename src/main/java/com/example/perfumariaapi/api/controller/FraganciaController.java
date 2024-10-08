package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.FragranciaDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;

import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.FragranciaService;
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
@RequestMapping("/api/v1/fragrancias")
@RequiredArgsConstructor
@CrossOrigin

public class FraganciaController {
    private final FragranciaService service;
    private final ProdutoService produtoService;
    @GetMapping()
    @ApiOperation("Visualizar fragancia" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fragancia encontrado"),
            @ApiResponse(code = 404, message = "Fragancia não encontrado")
    })
    public ResponseEntity get() {
        List<Fragrancia> fragrancias = service.getFragrancias();
        return ResponseEntity.ok(fragrancias.stream().map(FragranciaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detahes de uma fragancia" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fragancia encontrado"),
            @ApiResponse(code = 404, message = "Fragancia não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fragrancia> fragrancia = service.getFragranciaById(id);
        if (!fragrancia.isPresent()) {
            return new ResponseEntity("Fragrancia não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fragrancia.map(FragranciaDTO::create));
    }

    @GetMapping("{id}/produtos")
    public ResponseEntity getProdutos(@PathVariable("id") Long id) {
        Optional<Fragrancia> fragrancia = service.getFragranciaById(id);
        if (!fragrancia.isPresent()) {
            return new ResponseEntity("Não existe produto com essa fragrancia", HttpStatus.NOT_FOUND);
        }
        List<Produto> produtos = produtoService.getProdutosByFragrancia(fragrancia);
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova fragancia" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fragancia salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar nova fragancia")
    })
    public ResponseEntity post(@RequestBody FragranciaDTO dto) {
        try {
            Fragrancia fragrancia = converter(dto);
            fragrancia = service.salvar(fragrancia);
            return new ResponseEntity(fragrancia, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar fragancia" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fragancia atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar fragancia")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody  FragranciaDTO dto) {
        if (!service.getFragranciaById(id).isPresent()) {
            return new ResponseEntity("Fragrancia não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fragrancia fragrancia = converter(dto);
            fragrancia.setId(id);
            service.salvar(fragrancia);
            return ResponseEntity.ok(fragrancia);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Deletar fragancia" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fragancia excluída com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao exluir fragancia")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Fragrancia> fragrancia = service.getFragranciaById(id);
        if (!fragrancia.isPresent()) {
            return new ResponseEntity("Fragrancia não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fragrancia.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Fragrancia converter(FragranciaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Fragrancia.class);
    }

}
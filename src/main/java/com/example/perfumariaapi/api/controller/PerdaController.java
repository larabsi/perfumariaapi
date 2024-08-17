package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.PerdaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Perda;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.PerdaService;
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
@RequestMapping("/api/v1/perdas")
@RequiredArgsConstructor
@CrossOrigin

public class PerdaController {
    private final PerdaService service;
    private final ProdutoService produtoService;

    @GetMapping()
    @ApiOperation("Visualizar perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Perda encontrada"),
            @ApiResponse(code = 404, message = "Perda não encontrada")
    })
    public ResponseEntity get() {
        List<Perda> perdas = service.getPerda();
        return ResponseEntity.ok(perdas.stream().map(PerdaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Perda encontrada"),
            @ApiResponse(code = 404, message = "Perda não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Perda> perda = service.getPerdaById(id);
        if (!perda.isPresent()) {
            return new ResponseEntity("Perda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(perda.map(PerdaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Perda salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Perda")
    })
    public ResponseEntity post( @RequestBody PerdaDTO dto) {
        try {
            Perda perda = converter(dto);
            perda = service.salvar(perda);
            return new ResponseEntity(perda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Perda atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Perda")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PerdaDTO dto) {
        if (!service.getPerdaById(id).isPresent()) {
            return new ResponseEntity("Perda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Perda perda = converter(dto);
            perda.setId(id);
            service.salvar(perda);
            return ResponseEntity.ok(perda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Perda excluida com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Perda")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Perda> perda = service.getPerdaById(id);
        if (!perda.isPresent()) {
            return new ResponseEntity("Perda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(perda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Perda converter(PerdaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Perda perda = modelMapper.map(dto, Perda.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                perda.setProduto(null);
            } else{ perda.setProduto(produto.get());} }
        return perda;
    }

}

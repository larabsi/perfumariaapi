package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.FornecedorDTO;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.FornecedorService;
import com.example.perfumariaapi.service.PedidoService;
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
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
@CrossOrigin

public class FornecedorController {
    private final FornecedorService service;
    private final PedidoService pedidoService;

    @GetMapping()
    @ApiOperation("Visualizar Fornecedor" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity get() {
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Fornecedor" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor encontrado"),
            @ApiResponse(code = 404, message = "Fornecedor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }

    @GetMapping("{id}/pedidos")
    public ResponseEntity getPedidos(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Não existe esse pedido feito pelo fornecedor", HttpStatus.NOT_FOUND);
        }
        List<Pedido> pedidos = pedidoService.getPedidosByFornecedor(fornecedor);
        return ResponseEntity.ok(pedidos.stream().map(PedidoDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo Fornecedor" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Fornecedor")
    })
    public ResponseEntity post( @RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor = service.salvar(fornecedor);
            return new ResponseEntity(fornecedor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    @ApiOperation("Atualizar Fornecedor" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Fornecedor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FornecedorDTO dto) {
        if (!service.getFornecedorById(id).isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Fornecedor fornecedor = converter(dto);
            fornecedor.setId(id);
            service.salvar(fornecedor);
            return ResponseEntity.ok(fornecedor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar Fornecedor" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Fornecedor excluido com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Fornecedor")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedorById(id);
        if (!fornecedor.isPresent()) {
            return new ResponseEntity("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(fornecedor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        return fornecedor;
    }
}
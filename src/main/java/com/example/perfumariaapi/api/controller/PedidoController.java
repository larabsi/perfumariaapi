package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FornecedorDTO;
import com.example.perfumariaapi.api.dto.ListaPedidoDTO;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.FornecedorService;
import com.example.perfumariaapi.service.ListaPedidoService;
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
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@CrossOrigin

public class PedidoController {
    private final PedidoService service;
    private final FornecedorService fornecedorService;

    @GetMapping()
    @ApiOperation("Visualizar pedidos" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedidos encontrado"),
            @ApiResponse(code = 404, message = "Pedidos não encontrado")
    })
    public ResponseEntity get() {
        List<Pedido> pedidos = service.getPedidos();
        return ResponseEntity.ok(pedidos.stream().map(PedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um pedido" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedidos encontrado"),
            @ApiResponse(code = 404, message = "Pedidos não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pedido.map(PedidoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo pedido" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido salvo co sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Pedido")
    })
    public ResponseEntity post(@RequestBody PedidoDTO dto) {
        try {
            Pedido pedido = converter(dto);
            pedido = service.salvar(pedido);
            return new ResponseEntity(pedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/fornecedores")
    public ResponseEntity getFornecedores(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()) {
            return new ResponseEntity("Não existe fornecedor para esse pedido", HttpStatus.NOT_FOUND);
        }
        List<Fornecedor>fornecedor = fornecedorService.getFornecedoresByPedido(pedido);
        return ResponseEntity.ok(fornecedor.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar pedido" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Pedido")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PedidoDTO dto) {
        if (!service.getPedidoById(id).isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Pedido pedido = converter(dto);
            pedido.setId(id);
            service.salvar(pedido);
            return ResponseEntity.ok(pedido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar pedido" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido excuído com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Pedido")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pedido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Pedido converter(PedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pedido pedido = modelMapper.map(dto, Pedido.class);
        if(dto.getIdFornecedor() != 0) {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(dto.getIdFornecedor());
            if(!fornecedor.isPresent()){
                pedido.setFornecedor(null);
            } else{ pedido.setFornecedor(fornecedor.get());}
        }
        return pedido;
    }
}

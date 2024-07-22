package com.example.perfumariaapi.api.controller;


import com.example.perfumariaapi.api.dto.ListaPedidoDTO;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.ListaPedido;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.service.ListaPedidoService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/listaPedidos")
@RequiredArgsConstructor
@CrossOrigin

    public class ListaPedidoController {
    private final ListaPedidoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<ListaPedido> listaPedidos = service.getListaPedidos();
        return ResponseEntity.ok(listaPedidos.stream().map(ListaPedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ListaPedido> listaPedido = service.getListaPedidoById(id);
        if (!listaPedido.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaPedido.map(ListaPedidoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ListaPedidoDTO dto) {
        try {
            ListaPedido listaPedido = converter(dto);
            listaPedido = service.salvar(listaPedido);
            return new ResponseEntity(listaPedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ListaPedidoDTO dto) {
        if (!service.getListaPedidoById(id).isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ListaPedido listaPedido = converter(dto);
            listaPedido.setId(id);
            service.salvar(listaPedido);
            return ResponseEntity.ok(listaPedido);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ListaPedido> listaPedido = service.getListaPedidoById(id);
        if (!listaPedido.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(listaPedido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ListaPedido converter(ListaPedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, ListaPedido.class);
    }
}
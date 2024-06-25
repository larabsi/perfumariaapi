package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.FornecedorService;
import com.example.perfumariaapi.service.PedidoService;
import com.example.perfumariaapi.service.ProdutoService;
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
    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    @GetMapping()
    public ResponseEntity get() {
        List<Pedido> pedidos = service.getPedido();
        return ResponseEntity.ok(pedidos.stream().map(PedidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = service.getPedidoById(id);
        if (!pedido.isPresent()) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pedido.map(PedidoDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(PedidoDTO dto) {
        try {
            Pedido pedido = converter(dto);
            pedido = service.salvar(pedido);
            return new ResponseEntity(pedido, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Pedido converter(PedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pedido pedido = modelMapper.map(dto, Pedido.class);

        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                pedido.setProduto(null);
            } else{ pedido.setProduto(produto.get());}
        }
        if(dto.getIdFornecedor() !=0) {
            Optional<Fornecedor> fornecedor = fornecedorService.getFornecedorById(dto.getIdFornecedor());
            if(!fornecedor.isPresent()){

                pedido.setFornecedor(null);
            } else{ pedido.setFornecedor(fornecedor.get());}
        }
        return pedido;
    }
}

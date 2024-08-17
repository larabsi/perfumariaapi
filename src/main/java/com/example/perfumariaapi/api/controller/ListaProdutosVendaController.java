package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ListaProdutosVendaDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.ListaProdutosVendaService;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.VendaService;

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
@RequestMapping("/api/v1/listaProdutosVendas")
@RequiredArgsConstructor
@CrossOrigin

public class ListaProdutosVendaController {
    private final ListaProdutosVendaService service;

    private final ProdutoService produtoService;

    @GetMapping()
    @ApiOperation("Visualizar lista de produtos" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de produtos encontrada"),
            @ApiResponse(code = 404, message = "Lista de produtos não encontrada")
    })
    public ResponseEntity get() {
        List<ListaProdutosVenda> listaProdutosVendas = service.getListaProdutosVenda();
        return ResponseEntity.ok(listaProdutosVendas.stream().map(ListaProdutosVendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes da lista de produtos" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de produtos encontrada"),
            @ApiResponse(code = 404, message = "Lista de produtos não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<ListaProdutosVenda>listaProdutosVenda = service.getListaProdutosVendaById(id);
        if (!listaProdutosVenda.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaProdutosVenda.map(ListaProdutosVendaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar uma nova lista de produtos" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de produtos salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar nova lista de produtos")
    })
    public ResponseEntity post(@RequestBody ListaProdutosVendaDTO dto) {
        try {
            ListaProdutosVenda listaProdutosVenda = converter(dto);
            listaProdutosVenda = service.salvar(listaProdutosVenda);
            return new ResponseEntity(listaProdutosVenda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    @ApiOperation("Atualizar lista de produtos" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de produtos atualizar com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar lista de produtos")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ListaProdutosVendaDTO dto) {
        if (!service.getListaProdutosVendaById(id).isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            ListaProdutosVenda listaProdutosVenda = converter(dto);
            listaProdutosVenda.setId(id);
            service.salvar(listaProdutosVenda);
            return ResponseEntity.ok(listaProdutosVenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Deletar lista de produtos" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de produtos excluído com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir lista de produtos")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<ListaProdutosVenda> listaProdutosVenda = service.getListaProdutosVendaById(id);
        if (!listaProdutosVenda.isPresent()) {
            return new ResponseEntity("Lista não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(listaProdutosVenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ListaProdutosVenda converter(ListaProdutosVendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        ListaProdutosVenda listaProdutosVenda = modelMapper.map(dto, ListaProdutosVenda.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                listaProdutosVenda.setProduto(null);
            } else{
                listaProdutosVenda.setProduto(produto.get());
            }

        }
        return listaProdutosVenda;
    }

}

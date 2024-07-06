package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.api.dto.ItemDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.ItemService;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.VendaService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/itens")
@RequiredArgsConstructor
@CrossOrigin

public class ItemController {
    private final ItemService service;
    private final VendaService vendaService;
    private final ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Item> itens = service.getItem();
        return ResponseEntity.ok(itens.stream().map(ItemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Item> item = service.getItemById(id);
        if (!item.isPresent()) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(item.map(ItemDTO::create));
    }

    @GetMapping("{id}/vendas")
    public ResponseEntity getVendas(@PathVariable("id") Long id) {
        Optional<Item> item = service.getItemById(id);
        if (!item.isPresent()) {
            return new ResponseEntity("Item não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Venda> vendas = vendaService.getVendasByItem(item);
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ItemDTO dto) {
        try {
            Item item = converter(dto);
            item = service.salvar(item);
            return new ResponseEntity(item, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Item converter(ItemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(dto, Item.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto = produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                item.setProduto(null);
            } else{
                item.setProduto(produto.get());
            }
        }
        if(dto.getIdVendas() != null) {
            Optional<Venda> venda = vendaService.getVendaById(dto.getIdVendas());
            if(!venda.isPresent()){
                item.setVenda(null);
            } else{
                item.setVenda(venda.get());
            }
        }
        return item;
    }

}

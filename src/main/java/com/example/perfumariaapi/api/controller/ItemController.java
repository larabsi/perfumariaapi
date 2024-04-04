package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ItemDTO;
import com.example.perfumariaapi.model.entity.Item;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Venda;
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
    public Item converter(ItemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(dto, Item.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                item.setProduto(null);
            }
            else{
                item.setProduto(produto.get());
            }
        }
        if(dto.getIdVendas() !=0) {
            Optional<Venda> venda = vendaService.getVendaById(dto.getIdVendas());
            if(!venda.isPresent()){

                item.setVendas(null);
            }
            else{
                item.setVendas( venda.get());
            }
        }

        return item;
    }
}

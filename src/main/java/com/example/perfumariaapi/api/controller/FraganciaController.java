package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.CupomDTO;
import com.example.perfumariaapi.api.dto.FragranciaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cupom;
import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.FragranciaService;
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
@RequestMapping("/api/v1/fragrancias")
@RequiredArgsConstructor
@CrossOrigin

public class FraganciaController {
    private final FragranciaService service;
    private final ProdutoService produtoService;
    @GetMapping()
    public ResponseEntity get() {
        List<Fragrancia> fragrancias = service.getFragrancia();
        return ResponseEntity.ok(fragrancias.stream().map(FragranciaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Fragrancia> fragrancia = service.getFragranciaById(id);
        if (!fragrancia.isPresent()) {
            return new ResponseEntity("Fragrancia não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fragrancia.map(FragranciaDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(FragranciaDTO dto) {
        try {
            Fragrancia fragrancia = converter(dto);
            fragrancia = service.salvar(fragrancia);
            return new ResponseEntity(fragrancia, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Fragrancia converter(FragranciaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fragrancia fragrancia = modelMapper.map(dto, Fragrancia.class);
        if(dto.getIdProduto() != null) {
            Optional<Produto> produto= produtoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){
                fragrancia.setProduto(null);
            } else{
                fragrancia.setProduto(produto.get());
            }
        }
        return fragrancia;
    }
}

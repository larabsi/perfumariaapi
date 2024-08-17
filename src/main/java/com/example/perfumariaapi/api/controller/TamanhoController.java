package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.api.dto.TamanhoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.TamanhoService;
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
@RequestMapping("/api/v1/tamanhos")
@RequiredArgsConstructor
@CrossOrigin

public class TamanhoController {
    private final TamanhoService service;

    @GetMapping()
    @ApiOperation("Visualizar Tamanho" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity get() {
        List<Tamanho> tamanhos = service.getTamanhos();
        return ResponseEntity.ok(tamanhos.stream().map(TamanhoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um  tamanho" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho encontrado"),
            @ApiResponse(code = 404, message = "Tamanho não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Tamanho> tamanho = service.getTamanhoById(id);
        if (!tamanho.isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tamanho.map(TamanhoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar tamanho" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Tamanho")
    })
    public ResponseEntity post( @RequestBody TamanhoDTO dto) {
        try {
            Tamanho tamanho = converter(dto);
            tamanho = service.salvar(tamanho);
            return new ResponseEntity(tamanho, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar tamanho" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Tamanho")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TamanhoDTO dto) {
        if (!service.getTamanhoById(id).isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Tamanho tamanho = converter(dto);
            tamanho.setId(id);
            service.salvar(tamanho);
            return ResponseEntity.ok(tamanho);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar tamanho" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tamanho deletado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao exluir Tamanho")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Tamanho> tamanho = service.getTamanhoById(id);
        if (!tamanho.isPresent()) {
            return new ResponseEntity("Tamanho não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tamanho.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Tamanho converter(TamanhoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Tamanho.class);
    }

}

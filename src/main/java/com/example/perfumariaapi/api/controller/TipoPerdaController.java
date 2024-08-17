package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.TipoPerdaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.TipoPerda;
import com.example.perfumariaapi.service.TipoPerdaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipoPerdas")
@RequiredArgsConstructor
@CrossOrigin

public class TipoPerdaController {
    private final TipoPerdaService service;

    @GetMapping()
    @ApiOperation("Visualizar Tipo de perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de perda encontrado"),
            @ApiResponse(code = 404, message = "Tipo de perda não encontrado")
    })
    public ResponseEntity get() {
        List<TipoPerda> tipoPerdas = service.getTipoPerdas();
        return ResponseEntity.ok(tipoPerdas.stream().map(TipoPerdaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Tipo de perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de perda encontrado"),
            @ApiResponse(code = 404, message = "Tipo de perda não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoPerda> tipoPerda = service.getTipoPerdaById(id);
        if (!tipoPerda.isPresent()) {
            return new ResponseEntity("Tipoda Perda não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoPerda.map(TipoPerdaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar Tipo de perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de perda salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Tipo de perda")
    })
    public ResponseEntity post( @RequestBody TipoPerdaDTO dto) {
        try {
            TipoPerda tipoPerda = converter(dto);
            tipoPerda = service.salvar(tipoPerda);
            return new ResponseEntity(tipoPerda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar Tipo de perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de perda atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Tipo de perda")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoPerdaDTO dto) {
        if (!service.getTipoPerdaById(id).isPresent()) {
            return new ResponseEntity("Tipo da perda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoPerda tipoPerda = converter(dto);
            tipoPerda.setId(id);
            service.salvar(tipoPerda);
            return ResponseEntity.ok(tipoPerda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar Tipo de perda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de perda excluído com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Tipo de perda")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoPerda> tipoPerda = service.getTipoPerdaById(id);
        if (!tipoPerda.isPresent()) {
            return new ResponseEntity("tipoPerda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoPerda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoPerda converter(TipoPerdaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoPerda.class);
    }

}


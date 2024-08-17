package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.EstadoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Estado;
import com.example.perfumariaapi.service.EstadoService;
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
@RequestMapping("/api/v1/estados")
@RequiredArgsConstructor
@CrossOrigin
public class EstadoController {
    private final EstadoService service;

    @GetMapping()
    @ApiOperation("Visualizar Estado" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado encontrado"),
            @ApiResponse(code = 404, message = "Estado não encontrado")
    })
    public ResponseEntity get() {
        List<Estado> estados = service.getEstados();
        return ResponseEntity.ok(estados.stream().map(EstadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de umEstado" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado encontrado"),
            @ApiResponse(code = 404, message = "Estado não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estado> estado = service.getEstadoById(id);
        if (!estado.isPresent()) {
            return new ResponseEntity("Endereço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estado.map(EstadoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo Estado" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Estado")
    })
    public ResponseEntity post(@RequestBody EstadoDTO dto) {
        try {
            Estado estado = converter(dto);
            estado = service.salvar(estado);
            return new ResponseEntity(estado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar Estado" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Estado")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EstadoDTO dto) {
        if (!service.getEstadoById(id).isPresent()) {
            return new ResponseEntity("estado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Estado estado = converter(dto);
            estado.setId(id);
            service.salvar(estado);
            return ResponseEntity.ok(estado);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar Estado" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado excluido com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Estado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Estado> estado = service.getEstadoById(id);
        if (!estado.isPresent()) {
            return new ResponseEntity("estado não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(estado.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Estado converter(EstadoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Estado.class);
    }
}
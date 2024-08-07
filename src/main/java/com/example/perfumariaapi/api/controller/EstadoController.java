package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.EstadoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Estado;
import com.example.perfumariaapi.service.EstadoService;
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
    public ResponseEntity get() {
        List<Estado> estados = service.getEstados();
        return ResponseEntity.ok(estados.stream().map(EstadoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Estado> estado = service.getEstadoById(id);
        if (!estado.isPresent()) {
            return new ResponseEntity("Endereço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(estado.map(EstadoDTO::create));
    }

    @PostMapping()
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
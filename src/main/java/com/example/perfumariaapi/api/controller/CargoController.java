package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.CargoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.service.CargoService;
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
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@CrossOrigin


public class CargoController {
    private final CargoService service;

    @GetMapping()
    @ApiOperation("Vizualizar um cargo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo encontrado"),
            @ApiResponse(code = 404, message = "Cargo não encontrado")
    })
    public ResponseEntity get() {
        List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cargo" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo encontrado"),
            @ApiResponse(code = 404, message = "Cargo não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo cargo" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar o cargo")
    })
    public ResponseEntity post( @RequestBody CargoDTO dto) {
        try {
            Cargo cargo = converter(dto);
            cargo = service.salvar(cargo);
            return new ResponseEntity(cargo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar  cargo" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar cargo")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CargoDTO dto) {
        if (!service.getCargoById(id).isPresent()) {
            return new ResponseEntity("cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cargo cargo = converter(dto);
            cargo.setId(id);
            service.salvar(cargo);
            return ResponseEntity.ok(cargo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar cargo" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo excluido com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Cargo")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cargo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cargo converter(CargoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cargo.class);
    }

}


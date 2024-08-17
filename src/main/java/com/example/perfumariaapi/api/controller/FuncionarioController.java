package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.FuncionarioDTO;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;

import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.CargoService;
import com.example.perfumariaapi.service.EstadoService;
import com.example.perfumariaapi.service.FuncionarioService;
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
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
@CrossOrigin

public class FuncionarioController {
    private final FuncionarioService service;
    private final CargoService cargoService;
    private final EstadoService estadoService;


    @GetMapping()
    @ApiOperation("Visualizar funcionário" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.getFuncionario();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um funcionário" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário encontrado"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um novo funcionário" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário salvo com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar novo funcionário")
    })
    public ResponseEntity post(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario funcionario = converter(dto);
            Cargo cargo = cargoService.salvar(funcionario.getCargo());
            funcionario.setCargo(cargo);
            funcionario = service.salvar(funcionario);
            return new ResponseEntity(funcionario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    @ApiOperation("Atualizar funcionário" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar funcionário")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FuncionarioDTO dto) {
        if (!service.getFuncionarioById(id).isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Funcionario funcionario = converter(dto);
            funcionario.setId(id);
            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Deletar funcionário" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funcionário excluído com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir funcionário")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.getFuncionarioById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionario não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        if(dto.getIdCargo() != null) {
            Optional<Cargo> cargo = cargoService.getCargoById(dto.getIdCargo());
            if(!cargo.isPresent()){
                funcionario.setCargo(null);
            }else {
                funcionario.setCargo(cargo.get());
            }
        }
        if(dto.getIdEstado() != null) {
            Optional<Estado> estado = estadoService.getEstadoById(dto.getIdEstado());
            if(!estado.isPresent()){
                funcionario.setEstado(null);
            }else {
                funcionario.setEstado(estado.get());
            }
        }
        return funcionario;
    }

}

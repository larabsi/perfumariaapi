package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FuncionarioDTO;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.*;
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
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
@CrossOrigin

public class VendaController {
    private final VendaService service;
    private final ProdutoService produtoService;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final CupomService cupomService;

    @GetMapping()
    @ApiOperation("Visualizar venda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity get() {
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma venda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrada"),
            @ApiResponse(code = 404, message = "Venda não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar venda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda salva com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao salvar Venda")
    })
    public ResponseEntity post(@RequestBody VendaDTO dto) {
        try {
            Venda venda = converter(dto);
            venda = service.salvar(venda);
            return new ResponseEntity(venda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualizar venda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao atualizar Venda")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VendaDTO dto) {
        if (!service.getVendaById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Venda venda = converter(dto);
            venda.setId(id);
            service.salvar(venda);
            return ResponseEntity.ok(venda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletar venda" )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda excluída com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao excluir Venda")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Venda> venda = service.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(venda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);
        if(dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());
            if(!cliente.isPresent()){
                venda.setCliente(null);
            }else {
                venda.setCliente(cliente.get());
            }
        }
        if(dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent())  {
                venda.setFuncionario(null);
            }else{
                venda.setFuncionario(funcionario.get());
            }
        }
        if(dto.getIdCupom() != null) {
            Optional<Cupom> cupom = cupomService.getCupomById(dto.getIdCupom());
            if(!cupom.isPresent()){
                venda.setCupom(null);
            }else{
                venda.setCupom(cupom.get());
            }
        }
        return venda;
    }
}

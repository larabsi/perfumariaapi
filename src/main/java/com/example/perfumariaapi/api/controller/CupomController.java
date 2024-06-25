package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.CupomDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.service.CupomService;
import com.example.perfumariaapi.model.entity.Cupom;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/cupons")
@RequiredArgsConstructor
public class CupomController {
    private final CupomService service;
    public Cupom converter(CupomDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cupom cupom = modelMapper.map(dto, Cupom.class);

        return cupom;
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Cupom> cupons = service.getCupons();
        return ResponseEntity.ok(cupons.stream().map(CupomDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cupom> cupom = service.getCupomById(id);
        if (!cupom.isPresent()) {
            return new ResponseEntity("Cupom não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cupom.map(CupomDTO::create));
    }
    @PostMapping()
    public ResponseEntity post(CupomDTO dto) {
        try {
            Cupom cupom = converter(dto);
            cupom = service.salvar(cupom);
            return new ResponseEntity(cupom, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

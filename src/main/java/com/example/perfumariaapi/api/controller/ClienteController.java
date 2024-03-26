package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Venda;
import com.example.perfumariaapi.service.VendaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.perfumariaapi.service.VendaService;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ClienteController {

    private final VendaService service;


    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);

        if(dto.getIdVendas() !=0) {
            Optional<Venda> vendas= VendaService.getVendaById(dto.getIdVendas());
            if(!vendas.isPresent()){

                cliente.setVenda(null);
            } else{ cliente.setVenda(vendas.get());} }



        return cliente;
    }
}

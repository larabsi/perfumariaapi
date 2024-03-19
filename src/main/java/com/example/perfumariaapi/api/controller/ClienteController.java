package com.example.perfumariaapi.api.controller;

import com.example.perfumariaapi.api.dto.ClienteDTO;
import com.example.perfumariaapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteController {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String numeroTelefone;
    private String dataNascimento;
    private Long idVendas;
    private Date data;
    private Double valor;

    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);

        return cliente;
    }
}

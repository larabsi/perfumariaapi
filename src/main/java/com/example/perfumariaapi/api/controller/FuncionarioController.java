package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FuncionarioDTO;
import com.example.perfumariaapi.model.entity.Funcionario;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Venda;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.VendaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@AllArgsConstructor
public class FuncionarioController {
    private final VendaService service;

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);

        if(dto.getIdVenda() !=0) {
            Optional<Venda> vendas= VendaService.getVendaById(dto.getIdVenda());
            if(!vendas.isPresent()){

                funcionario.setVenda(null);
            } else{ funcionario.setVenda(vendas.get());} }

        return funcionario;
    }
}

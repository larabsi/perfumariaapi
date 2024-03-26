package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.TamanhoDTO;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@AllArgsConstructor
public class TamanhoController {
    private final ProdutoService service;

    public Tamanho converter(TamanhoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Tamanho tamanho = modelMapper.map(dto, Tamanho.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                tamanho.setProduto(null);
            } else{ tamanho.setProduto(produto.get());} }

        return tamanho;
    }
}

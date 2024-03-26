package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FragranciaDTO;
import com.example.perfumariaapi.model.entity.Fragrancia;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@AllArgsConstructor
public class FraganciaController {

    private final ProdutoService service;

    public Fragrancia converter(FragranciaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fragrancia fragrancia = modelMapper.map(dto, Fragrancia.class);

        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto=ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                fragrancia.setProduto(null);
            } else{ fragrancia.setProduto(produto.get());} }

        return fragrancia;
    }
}

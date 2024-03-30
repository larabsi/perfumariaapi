package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Produto;
import  com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ClassificacaoController {
    private final ProdutoService service;
    public Classificacao converter(ClassificacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Classificacao classificacao = modelMapper.map(dto, Classificacao.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto=ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                classificacao.setProduto(null);
            } else{ classificacao.setProduto(produto.get());} }

        return classificacao;

    }
}

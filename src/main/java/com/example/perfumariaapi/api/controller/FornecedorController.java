package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FornecedorDTO;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@AllArgsConstructor
public class FornecedorController {
    private final ProdutoService service;

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                fornecedor.setProduto(null);
            } else{ fornecedor.setProduto(produto.get());} }

        return fornecedor;
    }
}

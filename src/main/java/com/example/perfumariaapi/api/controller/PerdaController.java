package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.PerdaDTO;
import com.example.perfumariaapi.model.entity.Perda;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.Optional;
@Data
@AllArgsConstructor
public class PerdaController {
    private final ProdutoService service;
    public Perda converter(PerdaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Perda perda = modelMapper.map(dto, Perda.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                perda.setProduto(null);
            } else{ perda.setProduto(produto.get());} }

        return perda;
    }
}

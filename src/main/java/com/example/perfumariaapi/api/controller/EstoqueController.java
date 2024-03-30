package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.EstoqueDTO;
import com.example.perfumariaapi.model.entity.Estoque;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.Optional;
@Data
@AllArgsConstructor
public class EstoqueController {
    private final ProdutoService service;
    public Estoque converter(EstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estoque estoque = modelMapper.map(dto, Estoque.class);

        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto=ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                estoque.setProduto(null);
            } else{ estoque.setProduto(produto.get());} }


        return estoque;
    }
}

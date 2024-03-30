package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ItemDTO;
import com.example.perfumariaapi.model.entity.Item;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.Venda;
import com.example.perfumariaapi.service.ProdutoService;
import com.example.perfumariaapi.service.VendaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.Optional;
@Data
@AllArgsConstructor
public class ItemController {
    private final VendaService vendaService;
    private final ProdutoService produtoService;
    public Item converter(ItemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(dto, Item.class);


        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                item.setProduto(null);
            }
            else{
                item.setProduto(produto.get());
            }
        }
        if(dto.getIdVendas() !=0) {
            Optional<Venda> venda = VendaService.getVendaById(dto.getIdVendas());
            if(!venda.isPresent()){

                item.setVendas(null);
            }
            else{
                item.setVendas( venda.get());
            }
        }

        return item;
    }
}

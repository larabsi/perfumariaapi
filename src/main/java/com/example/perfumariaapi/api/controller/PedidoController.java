package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.model.entity.Fornecedor;
import com.example.perfumariaapi.model.entity.Pedido;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.service.FornecedorService;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.Optional;
@Data
@AllArgsConstructor
public class PedidoController {
    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    public Pedido converter(PedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pedido pedido = modelMapper.map(dto, Pedido.class);

        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                pedido.setProduto(null);
            } else{ pedido.setProduto(produto.get());}
        }
        if(dto.getIdFornecedor() !=0) {
            Optional<Fornecedor> fornecedor = FornecedorService.getFornecedorById(dto.getIdFornecedor());
            if(!fornecedor.isPresent()){

                pedido.setFornecedor(null);
            } else{ pedido.setFornecedor(fornecedor.get());}
        }
        return pedido;
    }
}

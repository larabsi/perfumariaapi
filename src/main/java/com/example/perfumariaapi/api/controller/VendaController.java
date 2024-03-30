package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.model.entity.*;
import com.example.perfumariaapi.service.ClienteService;
import com.example.perfumariaapi.service.FuncionarioService;
import com.example.perfumariaapi.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.Optional;
@Data
@AllArgsConstructor
public class VendaController {
    private final ProdutoService produtoService;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);

        if(dto.getIdProduto() !=0) {
            Optional<Produto> produto= ProdutoService.getProdutoById(dto.getIdProduto());
            if(!produto.isPresent()){

                venda.setProduto(null);
            } else{ venda.setProduto(produto.get());}
        }
        if(dto.getIdCliente() !=0) {
            Optional<Cliente> cliente = ClienteService.getClienteById(dto.getIdCliente());
            if(!cliente.isPresent()){

                venda.setCliente(null);
            } else{ venda.setCliente(cliente.get());}
        }

        if(dto.getIdFuncionario() !=0) {
            Optional<Funcionario> funcionario = FuncionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                venda.setProduto(null);
            } else {
                venda.setFuncionario(funcionario.get());
            }
        }
        return venda;
    }
}

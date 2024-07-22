package com.example.perfumariaapi.api.dto;

import com.example.perfumariaapi.model.entity.ListaPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPedidoDTO {

    private Long id;
    private Double quantidade;
    private Double valor;
    private Long idProduto;


    public static ListaPedidoDTO create(ListaPedido listaPedido) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(listaPedido, ListaPedidoDTO.class);
    }
}

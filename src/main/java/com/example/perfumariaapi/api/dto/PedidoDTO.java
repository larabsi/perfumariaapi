package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private String valor;
    private String dataPedido;
    private String dataEntrega;
    private Long idFornecedor;
    private List<ListaPedidoDTO> listaPedido;


    public static PedidoDTO create(Pedido pedido) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pedido, PedidoDTO.class);
    }


}

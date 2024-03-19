package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.PedidoDTO;
import com.example.perfumariaapi.model.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoController {
    private Integer id;
    private String valor;
    private String dataPedido;
    private String dataEntrega;
    private Long idProduto;      //Qual produto foi feito pedido
    private Long idFornecedor;  // De qual forncedor comprou

    public Pedido converter(PedidoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pedido pedido = modelMapper.map(dto, Pedido.class);

        return pedido;
    }
}

package com.example.perfumariaapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer id;
    private String valor;
    private String dataPedido;
    private String dataEntrega;

}

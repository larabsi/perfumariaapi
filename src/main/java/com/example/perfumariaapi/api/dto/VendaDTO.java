package com.example.perfumariaapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Integer id;
    private String data;
    private String valorTotal;
    private String formaPagamento;
    private Long idCupom;
}

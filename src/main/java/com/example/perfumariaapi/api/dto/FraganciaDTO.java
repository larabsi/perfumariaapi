package com.example.perfumariaapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraganciaDTO {
    private Integer id;
    private String descricao;
    private Long idproduto;
}

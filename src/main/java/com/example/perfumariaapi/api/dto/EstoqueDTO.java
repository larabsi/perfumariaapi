package com.example.perfumariaapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Long id;
    private Integer quantidade;
    private Integer capacidadeMaxima;
    private Integer capacidadeMinima;
    private Integer pontoDeRessuprimento;
}

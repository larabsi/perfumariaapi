package com.example.perfumariaapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomDTO {
    private Long id;
    private String desconto;
    private Integer dataExpiracao;
    private String codigo;
}

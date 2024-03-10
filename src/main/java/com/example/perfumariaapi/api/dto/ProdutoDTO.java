package com.example.perfumariaapi.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Integer id;
    private String nome;
    private String codigoBarras;
    private Long idPerda;
}

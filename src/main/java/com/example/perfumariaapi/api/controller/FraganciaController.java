package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FragranciaDTO;
import com.example.perfumariaapi.model.entity.Fragrancia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraganciaController {
    private Integer id;
    private String descricao;
    private Long idProduto;
    private String nome;

    public Fragrancia converter(FragranciaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fragrancia fragrancia = modelMapper.map(dto, Fragrancia.class);

        return fragrancia;
    }
}

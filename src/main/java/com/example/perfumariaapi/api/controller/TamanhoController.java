package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.TamanhoDTO;
import com.example.perfumariaapi.model.entity.Tamanho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TamanhoController {
    private Integer id;
    private String volume;
    private Long idProduto;
    private String nome;

    public Tamanho converter(TamanhoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Tamanho tamanho = modelMapper.map(dto, Tamanho.class);

        return tamanho;
    }
}

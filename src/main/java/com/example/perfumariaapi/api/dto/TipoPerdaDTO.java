package com.example.perfumariaapi.api.dto;

import com.example.perfumariaapi.model.entity.Tamanho;
import com.example.perfumariaapi.model.entity.TipoPerda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPerdaDTO {
    private Long id;
    private String descricao;

    public static TipoPerdaDTO create(TipoPerda tipoPerda) {
        ModelMapper modelMapper = new ModelMapper();
        TipoPerdaDTO dto = modelMapper.map(tipoPerda, TipoPerdaDTO.class);
        return dto;
    }
}

package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Perda;
import com.example.perfumariaapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerdaDTO {
    private Long id;
    private String data;
    private String descricao;
    private Long idProduto;

    public static PerdaDTO create(Perda perda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(perda, PerdaDTO.class);
    }

}

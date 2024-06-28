package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Fragrancia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FragranciaDTO {
    private Long id;
    private String descricao;
    private Long idProduto;
    //private String nome;

    public static FragranciaDTO create(Fragrancia fragrancia) {
        ModelMapper modelMapper = new ModelMapper();
        FragranciaDTO dto = modelMapper.map(fragrancia, FragranciaDTO.class);
        return dto;
    }

}

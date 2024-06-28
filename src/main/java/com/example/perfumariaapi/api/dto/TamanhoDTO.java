package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Tamanho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TamanhoDTO {
    private Long id;
    private String volume;
    private Long idProduto;
    //private String nome;

    public static TamanhoDTO create(Tamanho tamanho) {
        ModelMapper modelMapper = new ModelMapper();
        TamanhoDTO dto = modelMapper.map(tamanho, TamanhoDTO.class);
        return dto;
    }
}

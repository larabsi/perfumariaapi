package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoDTO {
    private Long id;
    private String descricao;
    private Long idProduto;
    private String nome;
    public static ClassificacaoDTO create(Classificacao classificacao) {
        ModelMapper modelMapper = new ModelMapper();
        ClassificacaoDTO dto = modelMapper.map(classificacao, ClassificacaoDTO.class);
        return dto;
    }

}

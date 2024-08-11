package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String dataNascimento;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String cep;
    private String numeroTelefone;
    private String salario;
    private Long idCargo;
    private Long idEstado;


    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

}

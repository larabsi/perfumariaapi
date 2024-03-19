package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FuncionarioDTO;
import com.example.perfumariaapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioController {
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
    private String uf;
    private String cep;
    private String numeroTelefone;
    private String cargo;
    private String salario;
    private Long idVenda;

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);

        return funcionario;
    }
}

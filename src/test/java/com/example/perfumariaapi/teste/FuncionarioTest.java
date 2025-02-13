  package com.example.perfumariaapi.teste;

  import com.example.perfumariaapi.api.controller.FuncionarioController;
  import com.example.perfumariaapi.api.controller.ProdutoController;
  import com.example.perfumariaapi.api.dto.FuncionarioDTO;
  import com.example.perfumariaapi.api.dto.ProdutoDTO;
  import com.example.perfumariaapi.model.entity.*;
  import com.example.perfumariaapi.model.repository.*;
  import com.example.perfumariaapi.security.JwtService;
  import com.example.perfumariaapi.service.*;
  import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.security.crypto.password.PasswordEncoder;

  import java.util.Optional;

  import static org.junit.Assert.assertNotNull;
  import static org.junit.Assert.assertNull;
  import static org.junit.jupiter.api.Assertions.assertEquals;
  import static org.mockito.Mockito.mock;
  import static org.mockito.Mockito.when;

  @SpringBootTest
  class FuncionarioTest {

      @Autowired
      private PasswordEncoder passwordEncoder;
      @Autowired
      private JwtService jwtService;

      public FuncionarioDTO createFuncionario(Long id, String nome, String cpf, String email, String dataNascimento, String logradouro, Integer numero, String complemento, String bairro, String cidade, String cep, String numeroTelefone, String salario, Long idCargo, Long idEstado)
      {
          FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
          funcionarioDTO.setId(id);
          funcionarioDTO.setNome(nome);
          funcionarioDTO.setCpf(cpf);
          funcionarioDTO.setEmail(email);
          funcionarioDTO.setDataNascimento(dataNascimento);
          funcionarioDTO.setLogradouro(logradouro);
          funcionarioDTO.setNumero(numero);
          funcionarioDTO.setComplemento(complemento);
          funcionarioDTO.setBairro(bairro);
          funcionarioDTO.setCidade(cidade);
          funcionarioDTO.setCep(cep);
          funcionarioDTO.setNumeroTelefone(numeroTelefone);
          funcionarioDTO.setSalario(salario);
          funcionarioDTO.setIdCargo(idCargo);
          funcionarioDTO.setIdEstado(idEstado);

          return funcionarioDTO;
      }

      public FuncionarioController initializeFuncionarioController(FuncionarioDTO funcionarioDTO) {
          FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);
          CargoRepository cargoRepository = mock(CargoRepository.class);
          EstadoRepository estadoRepository = mock(EstadoRepository.class);

          FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);
          CargoService cargoService = new CargoService(cargoRepository);
          EstadoService estadoService = new EstadoService(estadoRepository);

          Cargo cargoMock = mock(Cargo.class);
          Estado estadoMock = mock(Estado.class);

          when(cargoRepository.findById(funcionarioDTO.getIdCargo())).thenReturn(Optional.of(cargoMock));
          when(estadoRepository.findById(funcionarioDTO.getIdEstado())).thenReturn(Optional.of(estadoMock));

          FuncionarioController funcionarioController = new FuncionarioController(funcionarioService, cargoService, estadoService);
          when(funcionarioRepository.findById(funcionarioDTO.getId())).thenReturn(Optional.of(new Funcionario()));

          return funcionarioController;
      }

      public Funcionario convert(FuncionarioController funcionarioController, FuncionarioDTO funcionarioDTO) {
          return funcionarioController.converter(funcionarioDTO);
      }

      @Test
      void Given_FuncionarioDTO_When_FullyFilled_Then_ShouldConvertSuccessfully() {
          FuncionarioDTO funcionarioDTO = createFuncionario(1L, "João Silva", "12345678901", "joao@email.com", "1990-01-01", "Rua A", 123, "Apto 10", "Centro", "São Paulo", "01000-000", "11999999999", "3000", 1L, 1L);
          FuncionarioController funcionarioController = initializeFuncionarioController(funcionarioDTO);
          Funcionario funcionario = convert(funcionarioController, funcionarioDTO);

          assertNotNull(funcionario);
          assertEquals(funcionarioDTO.getId(), funcionario.getId());
          assertEquals(funcionarioDTO.getNome(), funcionario.getNome());
          assertEquals(funcionarioDTO.getCpf(), funcionario.getCpf());
          assertEquals(funcionarioDTO.getEmail(), funcionario.getEmail());
      }

      @Test
      void Given_FuncionarioDTO_When_CargoIsNull_Then_ShouldSetCargoNull() {
          FuncionarioDTO funcionarioDTO = createFuncionario(1L, "João Silva", "12345678901", "joao@email.com", "1990-01-01",
                  "Rua A", 123, "Apto 10", "Centro", "São Paulo",
                  "01000-000", "11999999999", "3000", null, 1L);
          FuncionarioController funcionarioController = initializeFuncionarioController(funcionarioDTO);
          Funcionario funcionario = convert(funcionarioController, funcionarioDTO);

          assertNotNull(funcionario);
          assertEquals(funcionarioDTO.getId(), funcionario.getId());
          assertEquals(funcionarioDTO.getNome(), funcionario.getNome());
          assertEquals(funcionarioDTO.getCpf(), funcionario.getCpf());
          assertNull(funcionario.getCargo());
      }

      @Test
      void Given_FuncionarioDTO_When_CargoNotFound_Then_ShouldSetCargoNull() {
          FuncionarioDTO funcionarioDTO = createFuncionario(1L, "João Silva", "12345678901", "joao@email.com", "1990-01-01",
                  "Rua A", 123, "Apto 10", "Centro", "São Paulo",
                  "01000-000", "11999999999", "3000", 1L, 1L);
          FuncionarioController funcionarioController = initializeFuncionarioController(funcionarioDTO);
          funcionarioDTO.setIdCargo(999L);

          CargoRepository cargoRepository = mock(CargoRepository.class);
          CargoService cargoService = new CargoService(cargoRepository);

          when(cargoService.getCargoById(999L)).thenReturn(Optional.empty());
          Funcionario funcionario = convert(funcionarioController, funcionarioDTO);

          assertNotNull(funcionario);
          assertEquals(funcionarioDTO.getId(), funcionario.getId());
          assertEquals(funcionarioDTO.getNome(), funcionario.getNome());
          assertNull(funcionario.getCargo());
      }

      @Test
      void Given_FuncionarioDTO_When_EstadoIsNull_Then_ShouldSetEstadoNull() {
          FuncionarioDTO funcionarioDTO = createFuncionario(1L, "João Silva", "12345678901", "joao@email.com", "1990-01-01",
                  "Rua A", 123, "Apto 10", "Centro", "São Paulo",
                  "01000-000", "11999999999", "3000", 1L, null);
          FuncionarioController funcionarioController = initializeFuncionarioController(funcionarioDTO);
          Funcionario funcionario = convert(funcionarioController, funcionarioDTO);

          assertNotNull(funcionario);
          assertEquals(funcionarioDTO.getId(), funcionario.getId());
          assertEquals(funcionarioDTO.getNome(), funcionario.getNome());
          assertNull(funcionario.getEstado());
      }

      @Test
      void Given_FuncionarioDTO_When_EstadoNotFound_Then_ShouldSetEstadoNull() {
          FuncionarioDTO funcionarioDTO = createFuncionario(1L, "João Silva", "12345678901", "joao@email.com", "1990-01-01",
                  "Rua A", 123, "Apto 10", "Centro", "São Paulo",
                  "01000-000", "11999999999", "3000", 1L, 1L);
          FuncionarioController funcionarioController = initializeFuncionarioController(funcionarioDTO);
          funcionarioDTO.setIdEstado(999L);

          EstadoRepository estadoRepository = mock(EstadoRepository.class);
          EstadoService estadoService = new EstadoService(estadoRepository);

          when(estadoService.getEstadoById(999L)).thenReturn(Optional.empty());
          Funcionario funcionario = convert(funcionarioController, funcionarioDTO);

          assertNotNull(funcionario);
          assertEquals(funcionarioDTO.getId(), funcionario.getId());
          assertEquals(funcionarioDTO.getNome(), funcionario.getNome());
          assertNull(funcionario.getEstado());
      }

  }

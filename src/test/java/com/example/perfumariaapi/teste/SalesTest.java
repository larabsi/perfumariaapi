  package com.example.perfumariaapi.teste;

  import com.example.perfumariaapi.api.controller.ProdutoController;
  import com.example.perfumariaapi.api.controller.VendaController;
  import com.example.perfumariaapi.api.dto.ListaProdutosVendaDTO;
  import com.example.perfumariaapi.api.dto.ProdutoDTO;
  import com.example.perfumariaapi.api.dto.VendaDTO;
  import com.example.perfumariaapi.model.entity.*;
  import com.example.perfumariaapi.model.repository.*;
  import com.example.perfumariaapi.security.JwtService;
  import com.example.perfumariaapi.service.*;
  import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.security.crypto.password.PasswordEncoder;

  import java.util.List;
  import java.util.Optional;

  import static org.junit.Assert.*;
  import static org.junit.jupiter.api.Assertions.assertEquals;
  import static org.mockito.Mockito.mock;
  import static org.mockito.Mockito.when;

  @SpringBootTest
  class SalesTest {

      @Autowired
      private PasswordEncoder passwordEncoder;
      @Autowired
      private JwtService jwtService;
      @Autowired
      private ProdutoService produtoService;

      public VendaDTO createSales(Long id, String data,String formaPagamento, Long idCupom, Long idCliente, Long idFuncionario, String valorTotal, List<ListaProdutosVendaDTO> listaProdutosVenda)
      {
          VendaDTO salesDTO = new VendaDTO();
          salesDTO.setId(id);
          salesDTO.setData(data);
          salesDTO.setFormaPagamento(formaPagamento);
          salesDTO.setIdCupom(idCupom);
          salesDTO.setIdCliente(idCliente);
          salesDTO.setIdFuncionario(idFuncionario);
          salesDTO.setValor_total(valorTotal);
          salesDTO.setListaProdutosVenda(listaProdutosVenda);
          return salesDTO;
      }

      public VendaController initializeSalesController(VendaDTO salesDTO)
      {
          VendaRepository salesRepository = mock(VendaRepository.class);
          CupomRepository cupomRepository = mock(CupomRepository.class);
          ClienteRepository clienteRepository = mock(ClienteRepository.class);
          FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);
          ListaProdutosVendaRepository listaProdutosVendaRepository = mock(ListaProdutosVendaRepository.class);
          ProdutoRepository produtoRepository = mock(ProdutoRepository.class);

          VendaService salesService = new VendaService(salesRepository);
          CupomService cupomService = new CupomService(cupomRepository);
          ClienteService clienteService = new ClienteService(clienteRepository);
          FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);
          ListaProdutosVendaService listaProdutosVendaService = new ListaProdutosVendaService(listaProdutosVendaRepository);
          ProdutoService produtoService = new ProdutoService(produtoRepository);

          Cupom cupomMock = mock(Cupom.class);
          Cliente clienteMock = mock(Cliente.class);
          Funcionario funcionarioMock = mock(Funcionario.class);
          ListaProdutosVenda listaProdutosVendaMock = mock(ListaProdutosVenda.class);
          Produto produtoMock = mock(Produto.class);

          when(cupomRepository.findById(salesDTO.getIdCupom())).thenReturn(Optional.of(cupomMock));
          when(clienteRepository.findById(salesDTO.getIdCliente())).thenReturn(Optional.of(clienteMock));
          when(funcionarioRepository.findById(salesDTO.getIdCliente())).thenReturn(Optional.of(funcionarioMock));
          when(listaProdutosVendaRepository.findById(salesDTO.getListaProdutosVenda().get(0).getId())).thenReturn(Optional.of(listaProdutosVendaMock));
          when(produtoRepository.findById(salesDTO.getListaProdutosVenda().get(0).getId())).thenReturn(Optional.of(produtoMock));

          VendaController vendaController = new VendaController(salesService, produtoService, funcionarioService, clienteService,cupomService);
          when(salesRepository.findById(salesDTO.getId())).thenReturn(Optional.of(new Venda()));
          return vendaController;
      }

      public Venda convert(VendaController salesController,VendaDTO salesDTO)
      {
          return salesController.converter(salesDTO);
      }

      @Test
      void Given_VendaDTO_When_FullyFilled_Then_ShouldConvertSuccessfully()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));  // Produto ID: 1, Quantidade: 2
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", 1L, 1L, 1L, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertNotNull(venda.getCliente());
          assertNotNull(venda.getFuncionario());
          assertNotNull(venda.getCupom());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
      }

      @Test
      void Given_VendaDTO_When_ClienteIsNull_Then_ShouldSetClienteNull()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", 1L, 1L, 1L, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);
          salesDTO.setIdCliente(null);

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
          assertNull(venda.getCliente());
      }

      @Test
      void Given_VendaDTO_When_ClienteNotFound_Then_ShouldSetClienteNull()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", 1L, 1L, 1L, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);
          salesDTO.setIdCliente(999L);

          ClienteRepository clienteRepository = mock(ClienteRepository.class);
          ClienteService clienteService = new ClienteService(clienteRepository);

          when(clienteService.getClienteById(999L)).thenReturn(Optional.empty());

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
          assertNull(venda.getCliente());
      }

      @Test
      void Given_VendaDTO_When_FuncionarioIsNull_Then_ShouldSetFuncionarioNull()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", 1L, 1L, null, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
          assertNull(venda.getFuncionario());
      }

      @Test
      void Given_VendaDTO_When_FuncionarioNotFound_Then_ShouldSetFuncionarioNull()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", 1L, 1L, 1L, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);
          salesDTO.setIdFuncionario(999L);

          FuncionarioRepository funcionarioRepository = mock(FuncionarioRepository.class);
          FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);

          when(funcionarioService.getFuncionarioById(999L)).thenReturn(Optional.empty());

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
          assertNull(venda.getFuncionario());
      }

      @Test
      void Given_VendaDTO_When_CupomIsNull_Then_ShouldSetSizeNull()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", null, 1L, 1L, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
          assertNull(venda.getCupom());
      }

      @Test
      void Given_VendaDTO_When_CupomNotFound_Then_ShouldSetCupomNull()
      {
          List<ListaProdutosVendaDTO> produtos = List.of(new ListaProdutosVendaDTO(1L, 2D,2D,1L));
          VendaDTO salesDTO = createSales(1L, "2025-02-12", "CREDITO", 1L, 1L, 1L, "150.00", produtos);
          VendaController salesController = initializeSalesController(salesDTO);
          salesDTO.setIdCupom(999L);

          CupomRepository cupomRepository = mock(CupomRepository.class);
          CupomService cupomService = new CupomService(cupomRepository);

          when(cupomService.getCupomById(999L)).thenReturn(Optional.empty());

          Venda venda = convert(salesController,salesDTO);

          assertNotNull(venda);
          assertEquals(salesDTO.getId(), venda.getId());
          assertEquals(salesDTO.getData(), venda.getData());
          assertEquals(salesDTO.getFormaPagamento(), venda.getFormaPagamento());
          assertEquals(salesDTO.getValor_total(), venda.getValor_total());
          assertFalse(salesDTO.getListaProdutosVenda().isEmpty());
          assertNull(venda.getCupom());
      }

  }

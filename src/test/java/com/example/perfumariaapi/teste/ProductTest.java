  package com.example.perfumariaapi.teste;

import com.example.perfumariaapi.api.controller.ProdutoController;

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
class ProductTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

	public ProdutoDTO createProduct(Long id, String product, Long idClassificacao, Long idFragrancia, Long idTamanho, String codigoBarras)
	{
		ProdutoDTO productDTO = new ProdutoDTO();
		productDTO.setId(id);
		productDTO.setProduto(product);
		productDTO.setIdClassificacao(idClassificacao);
		productDTO.setIdFragrancia(idFragrancia);
		productDTO.setIdTamanho(idTamanho);
		productDTO.setCodigoBarras(codigoBarras);

		return productDTO;
	}

	public ProdutoController initializeProductController(ProdutoDTO productDTO)
	{
		ProdutoRepository productRepository = mock(ProdutoRepository.class);
		TamanhoRepository tamanhoRepository = mock(TamanhoRepository.class);
		FragranciaRepository fragranciaRepository = mock(FragranciaRepository.class);
		EstoqueRepository estoqueRepository = mock(EstoqueRepository.class);
		ClassificacaoRepository classificacaoRepository = mock(ClassificacaoRepository.class);

		ProdutoService productService = new ProdutoService(productRepository);
		TamanhoService tamanhoService = new TamanhoService(tamanhoRepository);
		FragranciaService fragranciaService = new FragranciaService(fragranciaRepository);
		EstoqueService estoqueService = new EstoqueService(estoqueRepository);
		ClassificacaoService classificacaoService = new ClassificacaoService(classificacaoRepository);

		Tamanho tamanhoMock = mock(Tamanho.class);
		Fragrancia fragranciaMock = mock(Fragrancia.class);
		Classificacao classificacaoMock = mock(Classificacao.class);

		when(tamanhoRepository.findById(productDTO.getIdTamanho())).thenReturn(java.util.Optional.of(tamanhoMock));
		when(fragranciaRepository.findById(productDTO.getIdFragrancia())).thenReturn(java.util.Optional.of(fragranciaMock));
		when(classificacaoRepository.findById(productDTO.getIdClassificacao())).thenReturn(java.util.Optional.of(classificacaoMock));

		ProdutoController productController = new ProdutoController(productService, tamanhoService, classificacaoService, fragranciaService,estoqueService);
		when(productRepository.findById(productDTO.getId())).thenReturn(java.util.Optional.of(new Produto()));
		return productController;
	}

	public Produto convert(ProdutoController productController,ProdutoDTO productDTO)
	{
		return productController.converter(productDTO);
	}

	@Test
	void Given_ProductDTO_When_FullyFilled_Then_ShouldConvertSuccessfully()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", 1L, 1L, 1L, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
	}

	@Test
	void Given_ProductDTO_When_ClassificationIsNull_Then_ShouldSetClassificationNull()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", null, 1L, 1L, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
		assertNull(produto.getClassificacao());
	}

	@Test
	void Given_ProductDTO_When_ClassificationNotFound_Then_ShouldSetClassificationNull()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", 1L, 1L, 1L, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		productDTO.setIdClassificacao(999L);

		ClassificacaoRepository classificacaoRepository = mock(ClassificacaoRepository.class);
		ClassificacaoService classificacaoService = new ClassificacaoService(classificacaoRepository);

		when(classificacaoService.getClassificacaoById(999L)).thenReturn(Optional.empty());
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
		assertNull(produto.getClassificacao());
	}

	@Test
	void Given_ProductDTO_When_FragranceIsNull_Then_ShouldSetFragranceNull()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", 1L, null, 1L, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
		assertNull(produto.getFragrancia());
	}

	@Test
	void Given_ProductDTO_When_FragranceNotFound_Then_ShouldSetFragranceNull()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", 1L, 1L, 1L, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		productDTO.setIdFragrancia(999L);

		FragranciaRepository fragranciaRepository = mock(FragranciaRepository.class);
		FragranciaService fragranciaService = new FragranciaService(fragranciaRepository);

		when(fragranciaService.getFragranciaById(999L)).thenReturn(Optional.empty());
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
		assertNull(produto.getFragrancia());
	}

	@Test
	void Given_ProductDTO_When_SizeIsNull_Then_ShouldSetSizeNull()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", 1L, 1L, null, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
		assertNull(produto.getTamanho());
	}

	@Test
	void Given_ProductDTO_When_SizeNotFound_Then_ShouldSetSizeNull()
	{
		ProdutoDTO productDTO = createProduct(1L, "Produto A", 1L, 1L, 1L, "1234567890123");
		ProdutoController productController = initializeProductController(productDTO);
		productDTO.setIdTamanho(999L);

		TamanhoRepository tamanhoRepository = mock(TamanhoRepository.class);
		TamanhoService tamanhoService = new TamanhoService(tamanhoRepository);

		when(tamanhoService.getTamanhoById(999L)).thenReturn(Optional.empty());
		Produto produto = convert(productController, productDTO);

		assertNotNull(produto);
		assertEquals(productDTO.getId(), produto.getId());
		assertEquals(productDTO.getProduto(), produto.getProduto());
		assertEquals(productDTO.getCodigoBarras(), produto.getCodigoBarras());
		assertNull(produto.getTamanho());
	}

}

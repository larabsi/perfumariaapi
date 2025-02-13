package com.example.perfumariaapi.teste;
import com.example.perfumariaapi.api.controller.UsuarioController;
import com.example.perfumariaapi.api.dto.UsuarioDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Usuario;
import com.example.perfumariaapi.model.repository.UsuarioRepository;
import com.example.perfumariaapi.security.JwtService;
import com.example.perfumariaapi.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

	public UsuarioDTO createUser(Long id, String login, String cpf, String senha, String senhaRepeticao, boolean admin)
	{
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setId(id);
		userDTO.setLogin(login);
		userDTO.setCpf(cpf);
		userDTO.setSenha(senha);
		userDTO.setSenhaRepeticao(senhaRepeticao);
		userDTO.setAdmin(admin);
		return userDTO;
	}

	public UsuarioController initializeUsuarioController(UsuarioDTO userDTO)
	{
		UsuarioRepository userRepository = mock(UsuarioRepository.class);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UsuarioService userService = new UsuarioService(userRepository);
		JwtService jwtService = mock(JwtService.class);
		UsuarioController userController = new UsuarioController(userService, passwordEncoder, userService, jwtService);
		when(userRepository.findById(userDTO.getId())).thenReturn(java.util.Optional.of(new Usuario()));
		return userController;
	}

	public ResponseEntity updateUser(UsuarioController userController,Long id,UsuarioDTO userDTO)
	{
		return userController.atualizar(id, userDTO);
	}

	@Test
	void Given_ExistingUser_When_Updated_Then_UserShouldBeUpdatedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", "123456","123456", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(200, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 200");
	}

	@Test
	void Given_User_When_IdNotFound_Then_UpdateShouldFail()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(2L, "Lara Ferreira", "22222222222", "123456","123456", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(404, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 404");
	}

	@Test
	void Given_User_When_UpdatedWithEmptyPassword_Then_UpdateShouldFail()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", "","123456", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), " A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_UpdatedWithEmptyRepeatPassword_Then_UpdateShouldFail()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", "123456","", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), " A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_UpdatedWithNullPassword_Then_UpdateShouldFail()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", null,"", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), " A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_UpdatedWithNullRepeatPassword_Then_UpdateShouldFail()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", "123456",null, true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), " A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_UpdatedWithPasswordAndRepeatPasswordDifferent_Then_UpdateShouldFail()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", "123456","654321", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), " A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_UpdatedSuccessfully_Then_ReturnUpdatedUserValues()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, "Lara Ferreira", "22222222222", "123456","123456", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(200, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 200");

		Usuario updatedUser = (Usuario) responseEntity.getBody();
		assertNotNull(updatedUser);
		System.out.println("Usuário atualizado:");
		System.out.println("ID: " + updatedUser.getId());
		System.out.println("Login: " + updatedUser.getLogin());
		System.out.println("CPF: " + updatedUser.getCpf());
		System.out.println("Senha: " + updatedUser.getSenha());
	}

	@Test
	void Given_User_When_AllFieldsCorrectlyFilled_Then_ShouldBeSavedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","123456", true);
		UsuarioController userController = initializeUsuarioController(userDTO);
		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(201, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 200");
	}

	@Test
	void Given_User_When_PasswordFieldIsEmpty_Then_ShouldNotBeSavedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "","123456", true);
		UsuarioController userController = initializeUsuarioController(userDTO);
		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_PasswordConfirmationFieldIsEmpty_Then_ShouldNotBeSavedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","", true);
		UsuarioController userController = initializeUsuarioController(userDTO);
		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_PasswordIsNull_Then_ShouldNotBeSavedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", null,"123456", true);
		UsuarioController userController = initializeUsuarioController(userDTO);
		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_PasswordConfirmationIsNull_Then_ShouldNotBeSavedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456",null, true);
		UsuarioController userController = initializeUsuarioController(userDTO);
		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_PasswordAndPasswordConfirmationAreDifferent_Then_ShouldNotBeSavedSuccessfully()
	{
		UsuarioDTO userDTO =  createUser(1L, "Lara Lopes", "11111111111", "123456","654321", true);
		UsuarioController userController = initializeUsuarioController(userDTO);
		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}

	@Test
	void Given_User_When_ServiceThrowsRegraNegocioException_Then_ShouldReturnBadRequest()
	{
		UsuarioDTO userDTO = createUser(null, null, null, "123456", "123456", true);
		UsuarioController userController = initializeUsuarioController(userDTO);

		ResponseEntity<?> responseEntity = userController.post(userDTO);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}

	@Test
	void Given_UserUpdated_When_ServiceThrowsRegraNegocioException_Then_ShouldReturnBadRequest()
	{
		UsuarioDTO userDTO =  createUser(1L, null, null, "123456","123456", true);
		UsuarioDTO userDTO2 = createUser(1L, null, null, "123456","123456", true);

		ResponseEntity<?> responseEntity = updateUser(initializeUsuarioController(userDTO), userDTO2.getId(),userDTO2);

		assertNotNull(responseEntity);
		assertEquals(400, responseEntity.getStatusCodeValue(), "A resposta deveria retornar status 400");
	}
}

package com.moneyflow.moneyflow.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import com.moneyflow.moneyflow.service.UsuarioService;
import com.moneyflow.moneyflow.util.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioResourceTest {

	private MockMvc mockMvc;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private UsuarioService usuarioService;

	private ObjectWriter writer = new ObjectMapper().writer();

	@Before
	public void setUp() {
		UsuarioResource usuarioResource = new UsuarioResource(usuarioRepository, usuarioService);
		mockMvc = MockMvcBuilders.standaloneSetup(usuarioResource).build();
	}

	@Test
	public void save() throws Exception {

		Usuario mockUser = Fixtures.createMockUser(1L);

		when(usuarioRepository.save(any())).thenReturn(mockUser);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(writer.writeValueAsString(mockUser)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		verify(usuarioRepository).save(argThat(user -> user.equals(mockUser)));
	}

}

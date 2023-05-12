package com.moneyflow.moneyflow.resource;

import com.moneyflow.moneyflow.repository.RendimentoRepository;
import com.moneyflow.moneyflow.util.Fixtures;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RendimentosResourceTest {

	private MockMvc mockMvc;

	@InjectMocks
	private RendimentosResource rendimentosResource;

	@Mock
	private RendimentoRepository rendimentoRepository;

	@Before
	public void setup() {
		rendimentosResource = new RendimentosResource(rendimentoRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(rendimentosResource).build();
	}

	@Test
	public void getRendimentosByUsuarioRendimento() throws Exception {

		when(rendimentoRepository.findAllByUsuarioRendimentoId(1L))
				.thenReturn(List.of(Fixtures.createMockRendimento(1L)));

		mockMvc.perform(get("/api/rendimentos/usuario-rendimento/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].valor").value(new BigDecimal("1000")))
				.andExpect(jsonPath("$[0].descricao").value("Salario"));

		verify(rendimentoRepository).findAllByUsuarioRendimentoId(1L);
	}

}

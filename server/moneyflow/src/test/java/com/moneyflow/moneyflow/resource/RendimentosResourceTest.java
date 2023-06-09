package com.moneyflow.moneyflow.resource;

import com.client.common.dto.TransactionDTO;
import com.client.common.dto.TransactionDetailDTO;
import com.client.common.enums.TipoRendimento;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.repository.RendimentoRepository;
import com.moneyflow.moneyflow.service.UsuarioRendimentoService;
import com.moneyflow.moneyflow.util.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RendimentosResourceTest {

	private MockMvc mockMvc;

	@InjectMocks
	private RendimentosResource rendimentosResource;

	@Mock
	private RendimentoRepository rendimentoRepository;

	@Mock
	private UsuarioRendimentoService usuarioRendimentoService;

	@Before
	public void setup() {
		rendimentosResource = new RendimentosResource(usuarioRendimentoService, rendimentoRepository, usuarioRendimentoService);
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

	@Test
	public void salvar() throws Exception {

		UsuarioRendimento mockUserIncome = Fixtures.createMockUserIncome(1L);

		when(usuarioRendimentoService.salvar(any()))
				.thenReturn(mockUserIncome);

		mockMvc.perform(post("/api/rendimentos")
						.content(Fixtures.createMockJson(mockUserIncome))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		verify(usuarioRendimentoService).salvar(any());
	}

	@Test
	public void delete() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/rendimentos/{id}", 1L))
				.andExpect(status().isAccepted());

		verify(rendimentoRepository).deleteById(1L);
	}

	@Test
	public void salvarExtrato() throws Exception {

		TransactionDetailDTO detail = TransactionDetailDTO.of("Pagamento", BigDecimal.TEN, LocalDate.of(2023, 5, 1));

		TransactionDTO transaction = TransactionDTO.of("Teste", 1L, TipoRendimento.RECEITA, new ArrayList<>(List.of(detail)));

		List<TransactionDTO> transactions = new ArrayList<>(List.of(transaction));

		String mockJson = Fixtures.createMockJson(transactions);

		mockMvc.perform(post("/api/rendimentos/salvar-extrato")
				.content(mockJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		verify(usuarioRendimentoService).salvarExtrato(any());
	}

}

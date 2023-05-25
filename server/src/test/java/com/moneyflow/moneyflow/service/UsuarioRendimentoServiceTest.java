package com.moneyflow.moneyflow.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.dto.TransactionDetailDTO;
import com.client.common.enums.TipoRendimento;
import com.moneyflow.moneyflow.client.OfxParseFeignClient;
import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import com.moneyflow.moneyflow.util.Fixtures;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioRendimentoServiceTest {

	private UsuarioRendimentoService usuarioRendimentoService;

	private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);

	private final UsuarioRendimentoRepository usuarioRendimentoRepository = mock(UsuarioRendimentoRepository.class);

	private final OfxParseFeignClient ofxParseFeignClient = mock(OfxParseFeignClient.class);

	@Before
	public void setup() {
		usuarioRendimentoService = new UsuarioRendimentoService(usuarioRendimentoRepository, usuarioRepository, ofxParseFeignClient);
	}

	@Test
	public void salvar() {

		ArgumentCaptor<UsuarioRendimento> usuarioCalled = ArgumentCaptor.forClass(UsuarioRendimento.class);

		Usuario user = Fixtures.createMockUser(1L);

		when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));

		UsuarioRendimento mockUserIncome = Fixtures.createMockUserIncome(1L);
		mockUserIncome.assignUser(null);

		Rendimento mockRendimento = Fixtures.createMockRendimento(1L);
		mockRendimento.assign(null);

		mockUserIncome.getRendimentos().add(mockRendimento);

		usuarioRendimentoService.salvar(mockUserIncome);

		verify(usuarioRendimentoRepository, atLeastOnce()).save(usuarioCalled.capture());

		UsuarioRendimento saved = usuarioCalled.getValue();

		Assertions.assertThat(saved.getUsuario())
				.usingRecursiveComparison()
				.isEqualTo(user);

		Assertions.assertThat(saved.getRendimentos())
				.allSatisfy(rendimento -> Assertions.assertThat(rendimento.getUsuarioRendimento()).isEqualTo(saved));

		verify(usuarioRepository, atLeastOnce()).findById(1L);
		verifyNoMoreInteractions(usuarioRepository);

		verify(usuarioRendimentoRepository, atLeastOnce()).save(any(UsuarioRendimento.class));
		verifyNoMoreInteractions(usuarioRendimentoRepository);
	}

	@Test
	public void salvarOfx() throws IOException {

		ArgumentCaptor<List<UsuarioRendimento>> usuarioRendimento = ArgumentCaptor.forClass(List.class);

		TransactionDetailDTO detail = TransactionDetailDTO.of("Pagamento", BigDecimal.TEN, LocalDate.of(2023, 5, 1));

		TransactionDTO transaction = TransactionDTO.of("Teste", 1L, TipoRendimento.RECEITA, new ArrayList<>(List.of(detail)));

		when(usuarioRepository.findById(1L)).thenReturn(Optional.of(Fixtures.createMockUser(1L)));
		when(ofxParseFeignClient.parseOfx(any())).thenReturn(List.of(transaction));

		usuarioRendimentoService.salvarOfx(null);

		verify(ofxParseFeignClient, atLeastOnce()).parseOfx(any());
		verify(usuarioRendimentoRepository, atLeastOnce()).saveAll(usuarioRendimento.capture());

		List<UsuarioRendimento> rendimentos = usuarioRendimento.getValue();

		Assertions.assertThat(rendimentos)
				.hasSize(1)
				.allSatisfy(rendimento -> {
					Assertions.assertThat(rendimento.getDescricao()).isEqualTo(transaction.getDescricao());
					Assertions.assertThat(rendimento.getTipoRendimento()).isEqualTo(transaction.getTipoRendimento());
					Assertions.assertThat(rendimento.getCompetencia()).isEqualTo(transaction.getCompetencia());
				});

		Assertions.assertThat(rendimentos)
				.flatExtracting(UsuarioRendimento::getRendimentos)
				.hasSize(1)
				.allSatisfy(rendimentoSaved -> {
					Assertions.assertThat(rendimentoSaved.getUsuarioRendimento()).isNotNull();
					Assertions.assertThat(rendimentoSaved.getData()).isEqualTo(detail.getData());
					Assertions.assertThat(rendimentoSaved.getValor()).isEqualTo(detail.getValor());
					Assertions.assertThat(rendimentoSaved.getDescricao()).isEqualTo(detail.getDescricao());
				});

	}

}

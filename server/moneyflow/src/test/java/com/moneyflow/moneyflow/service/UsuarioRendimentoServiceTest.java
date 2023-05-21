package com.moneyflow.moneyflow.service;

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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioRendimentoServiceTest {

	private UsuarioRendimentoService usuarioRendimentoService;

	private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);

	private final UsuarioRendimentoRepository usuarioRendimentoRepository = mock(UsuarioRendimentoRepository.class);

	@Before
	public void setup() {
		usuarioRendimentoService = new UsuarioRendimentoService(usuarioRendimentoRepository, usuarioRepository);
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

}

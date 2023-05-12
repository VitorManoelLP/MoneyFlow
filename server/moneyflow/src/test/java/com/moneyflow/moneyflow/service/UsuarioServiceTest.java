package com.moneyflow.moneyflow.service;

import com.moneyflow.moneyflow.dto.InitialInformationsDTO;
import com.moneyflow.moneyflow.dto.RendimentoTotalDTO;
import com.moneyflow.moneyflow.enums.TipoRendimento;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import com.moneyflow.moneyflow.util.IntegrationTesting;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@IntegrationTesting
@Sql(statements = {
		"INSERT INTO usuario values(1, 'Fulano', 'Fulano@gmail.com', '1234', NULL, NULL, NULL, NULL);",

		"INSERT INTO tipo_rendimento values(2, 'Despesas');",
		"INSERT INTO tipo_rendimento values(1, 'Receitas');",

		"INSERT INTO usuario_rendimento values(1, 1, 'Gastos Gerais', 2, 12);",
		"INSERT INTO usuario_rendimento values(2, 1, 'Rendas gerais', 1, 12);",
		"INSERT INTO usuario_rendimento values(3, 1, 'Gastos Gerais', 2, 11);",

		"INSERT INTO rendimento values(1, 'Ifood', 100.00, '2020-12-01', 1);",
		"INSERT INTO rendimento values(2, 'Uber', 100.00, '2020-12-01', 1);",
		"INSERT INTO rendimento values(3, '99', 100.00, '2020-12-01', 1);",
		"INSERT INTO rendimento values(4, 'Uber Eats', 100.00, '2020-12-01', 1);",
		"INSERT INTO rendimento values(5, 'Rappi', 300.00, '2020-12-01', 1);",
		"INSERT INTO rendimento values(6, 'Internet', 200.00, '2020-12-24', 1);",
		"INSERT INTO rendimento values(7, 'Luz', 200.00, '2020-12-24', 1);",
		"INSERT INTO rendimento values(8, 'Água', 200.00, '2020-12-24', 1);",
		"INSERT INTO rendimento values(9, 'Gás', 200.00, '2020-12-24', 1);",

		"INSERT INTO rendimento values(10, 'Investimento Fundo Imobiliário', 100.00, '2020-12-01', 2);",
		"INSERT INTO rendimento values(11, 'Investimento Geral', 100.00, '2020-12-01', 2);",
		"INSERT INTO rendimento values(12, 'Salário', 700.00, '2020-12-18', 2);",
		"INSERT INTO rendimento values(13, 'Aluguel', 100.00, '2020-12-12', 2);",

		"INSERT INTO rendimento values(14, 'Ifood', 100.00, '2020-11-01', 3);"
})
public class UsuarioServiceTest {

	@Autowired
	private UsuarioRendimentoRepository usuarioRendimentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private UsuarioService usuarioService;

	@Before
	public void setup() {
		RendimentoService rendimentoService = new RendimentoService();
		usuarioService = new UsuarioService(usuarioRendimentoRepository, usuarioRepository, rendimentoService);
	}

	@Test
	public void initialInformations () {

		InitialInformationsDTO initialInformationsDTO = usuarioService.initialInformations(1L);

		Assertions.assertThat(initialInformationsDTO.getValorTotalDespesas())
				.isCloseTo(new BigDecimal("1600"), Assertions.within(new BigDecimal("0.01")));

		Assertions.assertThat(initialInformationsDTO.getValorTotalReceitas())
				.isCloseTo(new BigDecimal("1000"), Assertions.within(new BigDecimal("0.01")));

		Assertions.assertThat(initialInformationsDTO.getRendimentosTotais())
				.extracting(RendimentoTotalDTO::getNome)
				.containsOnly("Gastos Gerais", "Rendas gerais");

		Assertions.assertThat(initialInformationsDTO.getRendimentosTotais())
				.extracting(RendimentoTotalDTO::getValorTotal)
				.containsExactlyInAnyOrder(new BigDecimal("1500.00"), new BigDecimal("1000.00"), new BigDecimal("100.00"));

		Assertions.assertThat(initialInformationsDTO.getRendimentosTotais())
				.extracting(RendimentoTotalDTO::getTipoRendimento)
				.extracting(TipoRendimento::getDescricao)
				.containsExactlyInAnyOrder("Despesa", "Receita", "Despesa");

		Assertions.assertThat(initialInformationsDTO.getRendimentosTotais())
				.extracting(RendimentoTotalDTO::getMes)
				.containsExactlyInAnyOrder(11L, 12L, 12L);
	}

	@Test
	public void shouldThrowEntityNotFound () {
		Assertions.assertThatThrownBy(() -> usuarioService.initialInformations(1000L))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("Usuário não encontrado");
	}

}

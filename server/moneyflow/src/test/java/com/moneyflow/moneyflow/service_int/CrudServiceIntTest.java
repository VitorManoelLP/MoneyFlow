package com.moneyflow.moneyflow.service_int;

import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.repository.RendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import com.moneyflow.moneyflow.service.RendimentoService;
import com.moneyflow.moneyflow.service.UsuarioRendimentoService;
import com.moneyflow.moneyflow.util.IntegrationTesting;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@IntegrationTesting
@Sql(statements = {
		"INSERT INTO usuario values(1, 'Fulano', 'Fulano@gmail.com', null);",

		"INSERT INTO tipo_rendimento values(2, 'Despesas');",
		"INSERT INTO tipo_rendimento values(1, 'Receitas');",

		"INSERT INTO usuario_rendimento values(1, 1, 'Gastos Gerais 1', 2, 12);",
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

public class CrudServiceIntTest {

	private UsuarioRendimentoService usuarioRendimentoService;

	@Autowired
	private UsuarioRendimentoRepository usuarioRendimentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Before
	public void setup() {
		usuarioRendimentoService = new UsuarioRendimentoService(usuarioRendimentoRepository, usuarioRepository);
	}

	@Test
	public void findAll() {
		Page<UsuarioRendimento> all = usuarioRendimentoService.findAll("", Pageable.ofSize(10));
		Assertions.assertThat(all.getTotalElements()).isEqualTo(3);
	}

	@Test
	public void findAllWithSearch() {
		Page<UsuarioRendimento> all = usuarioRendimentoService.findAll("descricao==Rendas gerais", Pageable.ofSize(10));
		Assertions.assertThat(all.getTotalElements()).isEqualTo(1);
	}

	@Test
	public void findAllWithSearchNotEqual() {
		Page<UsuarioRendimento> all = usuarioRendimentoService.findAll("competencia!=11", Pageable.ofSize(10));
		Assertions.assertThat(all.getContent())
				.extracting(UsuarioRendimento::getCompetencia)
				.allSatisfy(competencia -> Assertions.assertThat(competencia).isNotEqualTo(11));
	}

	@Test
	public void findAllWithSearchIn() {
		Page<UsuarioRendimento> all = usuarioRendimentoService.findAll("descricao=in=Rendas gerais,Gastos Gerais 1", Pageable.ofSize(10));
		Assertions.assertThat(all.getContent())
				.extracting(UsuarioRendimento::getDescricao)
				.containsExactlyInAnyOrder("Rendas gerais", "Gastos Gerais 1");
	}

	@Test
	public void findAllWithSearchNotIn() {
		Page<UsuarioRendimento> all = usuarioRendimentoService.findAll("id=out=1,2", Pageable.ofSize(10));
		Assertions.assertThat(all.getContent())
				.extracting(UsuarioRendimento::getId)
				.containsExactlyInAnyOrder(3L);
	}

	@Test
	public void findAllWithSearchLike() {
		Page<UsuarioRendimento> all = usuarioRendimentoService.findAll("descricao=like=Gastos%", Pageable.ofSize(10));
		Assertions.assertThat(all.getContent())
				.extracting(UsuarioRendimento::getDescricao)
				.containsExactlyInAnyOrder("Gastos Gerais 1", "Gastos Gerais");
	}


}

package com.moneyflow.moneyflow.repository;

import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.util.IntegrationTesting;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@IntegrationTesting
@RunWith(SpringRunner.class)
@Sql(statements = {
		"INSERT INTO usuario values(1, 'Fulano', 'Fulano@gmail.com', null);",

		"INSERT INTO tipo_rendimento values(2, 'Despesas');",

		"INSERT INTO usuario_rendimento values(1, 1, 'Gastos Gerais', 2, 12);",

		"INSERT INTO rendimento values(1, 'Ifood', 100.00, '2020-12-01', 1);"
})
public class RendimentoRepositoryTest {

	@Autowired
	private RendimentoRepository rendimentoRepository;

	@Test
	public void findAllByUsuarioRendimentoId() {

		List<Rendimento> rendimentos = rendimentoRepository.findAllByUsuarioRendimentoId(1L);

		Assertions.assertThat(rendimentos)
				.hasSize(1)
				.extracting(rendimento -> rendimento.getUsuarioRendimento().getId())
				.containsOnly(1L);
	}

}

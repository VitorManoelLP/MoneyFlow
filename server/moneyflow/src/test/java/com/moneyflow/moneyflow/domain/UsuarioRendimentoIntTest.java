package com.moneyflow.moneyflow.domain;

import com.moneyflow.moneyflow.util.Fixtures;
import com.moneyflow.moneyflow.configurations.FlywayConfig;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.util.IntegrationTesting;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@IntegrationTesting
public class UsuarioRendimentoIntTest {

	@Autowired
	private UsuarioRendimentoRepository usuarioRendimentoRepository;

	@Test
	public void shouldSaveUsuario () {

		UsuarioRendimento mockUserIncome = Fixtures.createMockUserIncome(null);
		UsuarioRendimento save = usuarioRendimentoRepository.save(mockUserIncome);

		Assertions.assertThat(save).isNotNull();
	}

}

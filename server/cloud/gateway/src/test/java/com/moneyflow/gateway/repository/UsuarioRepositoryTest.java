package com.moneyflow.gateway.repository;

import com.moneyflow.gateway.domain.Usuario;
import com.moneyflow.gateway.util.IntegrationTesting;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@IntegrationTesting
@Sql(statements = {
		"INSERT INTO Role values(1, 'ROLE_ADMIN');",
		"INSERT INTO usuario values(1, 'Fulano', 'teste@gmail.com', '123', null, null, null, null);",
		"INSERT INTO USER_ROLE values(1, 1, 1);",
})
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	public void findOne() {

		Mono<Usuario> oneByEmail = usuarioRepository.findOneByEmail("teste@gmail.com");

		Assertions.assertThat(oneByEmail)
				.isNotNull()
				.extracting(Mono::block)
				.extracting(Usuario::getEmail)
				.isEqualTo("teste@gmail.com");
	}

}

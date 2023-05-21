package com.moneyflow.moneyflow.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.enums.TipoRendimento;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Fixtures {

	private Fixtures () {
	}

	public static Rendimento createMockRendimento (Long id) {
		return Rendimento.newInstance()
				.id(id)
				.usuarioRendimento(createMockUserIncome(id))
				.valor(BigDecimal.valueOf(1000))
				.descricao("Salario")
				.data(LocalDate.of(2019, 1, 1))
				.create();
	}

	public static UsuarioRendimento createMockUserIncome (Long id) {
		return UsuarioRendimento.newInstance()
				.id(id)
				.usuario(createMockUser(id))
				.tipoRendimento(TipoRendimento.RECEITA)
				.descricao("Salario")
				.create();
	}

	public static Usuario createMockUser (Long id) {
		return Usuario.newInstance()
				.id(id)
				.nome("Teste")
				.password("123456")
				.email("test@gmail.com")
				.create();
	}

	@SneakyThrows
	public static String createMockJson(Object obj) {
		ObjectWriter writer = new ObjectMapper().writer();
		return writer.writeValueAsString(obj);
	}

}

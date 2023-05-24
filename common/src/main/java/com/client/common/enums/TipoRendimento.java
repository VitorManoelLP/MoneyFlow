package com.client.common.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.stream.Collectors;

public enum TipoRendimento {
	RECEITA(1L, "Receita"),
	DESPESA(2L, "Despesa");

	TipoRendimento (Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	private final Long codigo;
	private final String descricao;

	public Long getCodigo () {
		return codigo;
	}

	public String getDescricao () {
		return descricao;
	}

	public static TipoRendimento getByValue (Long value) {
		return EnumUtils.getEnumList(TipoRendimento.class).stream()
				.collect(Collectors.toMap(TipoRendimento::getCodigo, tipoRendimento -> tipoRendimento))
				.get(value);
	}

}

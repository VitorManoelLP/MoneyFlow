package com.client.common.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.stream.Collectors;

public enum TipoRendimento {
	RECEITA(1L, "Receita", "CREDIT"),
	DESPESA(2L, "Despesa", "DEBIT");

	TipoRendimento (Long codigo, String descricao, String identificador) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.identificador = identificador;
	}

	private final Long codigo;
	private final String descricao;
	private final String identificador;

	public Long getCodigo () {
		return codigo;
	}

	public String getDescricao () {
		return descricao;
	}

	public String getIdentificador () {
		return identificador;
	}

	public static TipoRendimento getByValue (Long value) {
		return EnumUtils.getEnumList(TipoRendimento.class).stream()
				.collect(Collectors.toMap(TipoRendimento::getCodigo, tipoRendimento -> tipoRendimento))
				.get(value);
	}

	public static TipoRendimento getByIdentificador(String identificador) {
		return EnumUtils.getEnumList(TipoRendimento.class).stream()
				.collect(Collectors.toMap(TipoRendimento::getIdentificador, tipoRendimento -> tipoRendimento))
				.get(identificador);
	}

}

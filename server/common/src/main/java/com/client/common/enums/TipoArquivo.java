package com.client.common.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.stream.Collectors;

public enum TipoArquivo {

	OFX(1L, "OFX", "ofx"),
	CSV(2L, "CSV", "csv");

	TipoArquivo (Long codigo, String descricao, String identificador) {
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

	public static TipoArquivo getByValue (Long value) {
		return EnumUtils.getEnumList(TipoArquivo.class).stream()
				.collect(Collectors.toMap(TipoArquivo::getCodigo, tipoArquivo -> tipoArquivo))
				.get(value);
	}

	public static TipoArquivo getByIdentificador(String identificador) {
		return EnumUtils.getEnumList(TipoArquivo.class).stream()
				.collect(Collectors.toMap(TipoArquivo::getIdentificador, tipoArquivo -> tipoArquivo))
				.get(identificador);
	}


}

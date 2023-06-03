package com.client.common.dto;

import com.client.common.enums.TipoArquivo;
import org.springframework.lang.NonNull;

public class RequestParseDTO {

	@NonNull
	private String base64;

	@NonNull
	private TipoArquivo tipoArquivo;

	public RequestParseDTO (String base64, TipoArquivo tipoArquivo) {
		this.base64 = base64;
		this.tipoArquivo = tipoArquivo;
	}

	public String getBase64 () {
		return base64;
	}

	public void setBase64 (String base64) {
		this.base64 = base64;
	}

	public TipoArquivo getTipoArquivo () {
		return tipoArquivo;
	}

	public void setTipoArquivo (TipoArquivo tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}


}

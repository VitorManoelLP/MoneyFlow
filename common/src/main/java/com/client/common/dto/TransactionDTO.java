package com.client.common.dto;

import com.client.common.enums.TipoRendimento;

import java.util.ArrayList;
import java.util.List;

public class TransactionDTO {

	private String descricao;
	private Long competencia;
	private TipoRendimento tipoRendimento;
	private List<TransactionDetailDTO> details = new ArrayList<>();

	public TransactionDTO (String descricao, Long competencia, TipoRendimento tipoRendimento, List<TransactionDetailDTO> details) {
		this.descricao = descricao;
		this.competencia = competencia;
		this.tipoRendimento = tipoRendimento;
		this.details = details;
	}

	public String getDescricao () {
		return descricao;
	}

	public void setDescricao (String descricao) {
		this.descricao = descricao;
	}

	public Long getCompetencia () {
		return competencia;
	}

	public void setCompetencia (Long competencia) {
		this.competencia = competencia;
	}

	public TipoRendimento getTipoRendimento () {
		return tipoRendimento;
	}

	public void setTipoRendimento (TipoRendimento tipoRendimento) {
		this.tipoRendimento = tipoRendimento;
	}

	public List<TransactionDetailDTO> getDetails () {
		return details;
	}

	public void setDetails (List<TransactionDetailDTO> details) {
		this.details = details;
	}

}

package com.moneyflow.moneyflow.dto;

import com.client.common.enums.TipoRendimento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class RendimentoTotalDTO {

	@NonNull
	private TipoRendimento tipoRendimento;
	@NonNull
	private String nome;
	@Builder.Default
	private BigDecimal valorTotal = BigDecimal.ZERO;
	@NonNull
	private Long mes;
	@NonNull
	private Long idUsuarioRendimento;

}


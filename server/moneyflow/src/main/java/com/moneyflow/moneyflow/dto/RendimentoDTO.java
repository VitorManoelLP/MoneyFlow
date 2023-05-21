package com.moneyflow.moneyflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class RendimentoDTO {

	@Builder.Default
	private BigDecimal valor = BigDecimal.ZERO;

	@NonNull
	private String descricao;

	@NonNull
	private LocalDate data;

	@NonNull
	private Long idUsuarioRendimento;

	@NonNull
	private Long id;

}

package com.moneyflow.moneyflow.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitialInformationsDTO {

	@Builder.Default
	private BigDecimal valorTotalReceitas = BigDecimal.ZERO;

	@Builder.Default
	private BigDecimal valorTotalDespesas = BigDecimal.ZERO;

	@Builder.Default
	private List<RendimentoTotalDTO> rendimentosTotais = new ArrayList<>();

	@NonNull
	private String nomeUsuario;

}

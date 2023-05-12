package com.moneyflow.moneyflow.domain;

import com.moneyflow.moneyflow.dto.RendimentoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder(builderMethodName = "newInstance", buildMethodName = "create")
@Entity(name = "rendimento")
@Table(name = "rendimento")
@AllArgsConstructor
@NoArgsConstructor
public class Rendimento {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="rendimento_id_seq")
	@SequenceGenerator(name="rendimento_id_seq", sequenceName="rendimento_id_seq", allocationSize=1)
	private Long id;

	@NotEmpty
	private String descricao;

	@NotNull
	private BigDecimal valor;

	@NotNull
	private LocalDate data;

	@ManyToOne
	@JoinColumn(name = "usuario_rendimento_id")
	private UsuarioRendimento usuarioRendimento;

	public static RendimentoDTO convertToDTO(Rendimento rendimento) {
		return RendimentoDTO.builder()
				.descricao(rendimento.getDescricao())
				.valor(rendimento.getValor())
				.data(rendimento.getData())
				.idUsuarioRendimento(rendimento.getUsuarioRendimento().getId())
				.build();
	}

}

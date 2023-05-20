package com.moneyflow.moneyflow.domain;

import com.moneyflow.moneyflow.enums.TipoRendimento;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder(builderMethodName = "newInstance", buildMethodName = "create")
@Entity(name = "usuario_rendimento")
@Table(name = "usuario_rendimento")
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRendimento {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="usuario_rendimento_id_seq")
	@SequenceGenerator(name="usuario_rendimento_id_seq", sequenceName="usuario_rendimento_id_seq", allocationSize=1)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	@Setter(AccessLevel.PRIVATE)
	private Usuario usuario;

	@NotEmpty
	@Length(max = 255)
	private String descricao;

	@Column(name = "id_tipo_rendimento")
	private TipoRendimento tipoRendimento;

	@OneToMany(mappedBy = "usuarioRendimento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rendimento> rendimentos;

	private Long competencia;

	public static UsuarioRendimento ofUser(Usuario usuario) {
		UsuarioRendimento usuarioRendimento = new UsuarioRendimento();
		usuarioRendimento.setUsuario(usuario);
		return usuarioRendimento;
	}

}
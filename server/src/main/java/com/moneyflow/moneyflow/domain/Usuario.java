package com.moneyflow.moneyflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Builder(builderMethodName = "newInstance", buildMethodName = "create")
@Table(name = "usuario")
@Entity(name = "usuario")
@EqualsAndHashCode
@SQLDelete(sql = "UPDATE usuario SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuario_id_seq")
	@SequenceGenerator(name="usuario_id_seq", sequenceName="usuario_id_seq", allocationSize=1)
	private Long id;

	@NotEmpty
	@Length(max = 255)
	private String nome;

	@NotEmpty
	@Length(max = 255)
	private String email;

	@NotEmpty
	@Length(max = 255)
	private String password;

	private byte[] imagem;
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;

	@Column(name = "deleted_at")
	private Date deletedAt;

	@JsonIgnore
	@Formula("(deleted_at is null)")
	private Boolean isActive;

}

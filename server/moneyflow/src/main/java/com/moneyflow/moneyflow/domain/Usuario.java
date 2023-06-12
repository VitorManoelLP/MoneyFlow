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
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Length(max = 255)
	private String nome;

	@NotEmpty
	@Length(max = 255)
	private String email;

	private String imagem;

}

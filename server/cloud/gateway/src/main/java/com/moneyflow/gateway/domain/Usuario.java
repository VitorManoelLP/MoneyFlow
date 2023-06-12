package com.moneyflow.gateway.domain;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(builderMethodName = "newInstance", buildMethodName = "create")
@Table(name = "usuario")
@Entity(name = "usuario")
@SQLDelete(sql = "UPDATE usuario SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at is null")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

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

	@Formula("(deleted_at is null)")
	private Boolean isActive;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "usuario", fetch = FetchType.EAGER)
	private Set<UsuarioRole> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () {
		return roles.stream().map(UsuarioRole::getRole).collect(Collectors.toList());
	}

	@Override
	public String getUsername () {
		return nome;
	}

	@Override
	public boolean isAccountNonExpired () {
		return true;
	}

	@Override
	public boolean isAccountNonLocked () {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired () {
		return true;
	}

	@Override
	public boolean isEnabled () {
		return isActive;
	}
}

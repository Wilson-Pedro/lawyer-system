package com.advocacia.estacio.domain.entities;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.advocacia.estacio.domain.enums.UsuarioStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.advocacia.estacio.domain.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_USUARIO_AUTH")
public class UsuarioAuth implements UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String login;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Enumerated(EnumType.STRING)
	private UsuarioStatus usuarioStatus;

	public UsuarioAuth() {
	}
	
	public UsuarioAuth(String login, String password, UserRole role) {
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return switch(this.role) {
			case ADMIN -> List.of(
					new SimpleGrantedAuthority("ROLE_ADMIN"), 
					new SimpleGrantedAuthority("ROLE_COORDENADOR_DO_CURSO"),
					new SimpleGrantedAuthority("ROLE_SECRETARIO"),
					new SimpleGrantedAuthority("ROLE_PROFESSOR"),
					new SimpleGrantedAuthority("ROLE_ESTAGIARIO"),
					new SimpleGrantedAuthority("ROLE_ADVOGADO"));
			
			case COORDENADOR_DO_CURSO -> List.of(
					new SimpleGrantedAuthority("ROLE_COORDENADOR_DO_CURSO"));
			
			case SECRETARIO -> List.of(
					new SimpleGrantedAuthority("ROLE_SECRETARIO"));
			
			case PROFESSOR -> List.of(
					new SimpleGrantedAuthority("ROLE_PROFESSOR"));
			
			case ESTAGIARIO -> List.of(
					new SimpleGrantedAuthority("ROLE_ESTAGIARIO"));

			case ADVOGADO -> List.of(
					new SimpleGrantedAuthority("ROLE_ADVOGADO"));
			
			default -> throw new IllegalArgumentException("Role Inválida");
		};
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public UserRole getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, role);
	}

	public UsuarioStatus getUsuarioStatus() {
		return usuarioStatus;
	}

	public void setUsuarioStatus(UsuarioStatus usuarioStatus) {
		this.usuarioStatus = usuarioStatus;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioAuth other = (UsuarioAuth) obj;
		return Objects.equals(id, other.id) && Objects.equals(login, other.login)
				&& Objects.equals(password, other.password) && role == other.role;
	}
}

package org.lasalle.sigas.model.filter;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SolicitanteFilter {
	
	
	private String RA;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "Email é obrigatório")
	@Email
	private String email;
	
	@NotBlank(message = "Celular é obrigatório")
	private String celular;
	
	
	private String senha;

	public String getRA() {
		return RA;
	}

	public void setRA(String rA) {
		RA = rA;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((RA == null) ? 0 : RA.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitanteFilter other = (SolicitanteFilter) obj;
		if (RA == null) {
			if (other.RA != null)
				return false;
		} else if (!RA.equals(other.RA))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Nome :" + nome + " - " + " Email: " + email + " Celular " + celular;
	}

}

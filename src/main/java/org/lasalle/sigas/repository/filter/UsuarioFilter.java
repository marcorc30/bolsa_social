package org.lasalle.sigas.repository.filter;

import java.util.List;

import org.lasalle.sigas.model.Grupo;

public class UsuarioFilter {

	private String nome;
	private String cpf;
	private String email;
	private List<Grupo> grupos;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	

}

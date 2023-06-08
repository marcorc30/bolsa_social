package org.lasalle.sigas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Endereco implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "CEP é obrigatório")
	private String cep;
	
	@NotBlank(message = "Endereço é obrigatório")
	private String endereco;
	
	@NotBlank(message = "Cidade é obrigatório")
	private String cidade;
	
	@NotBlank(message = "Bairro é obrigatório")
	private String bairro;
	
	@NotBlank(message = "Estado é obrigatório")
	private String estado;

	private String complemento;
	
	private String numero;
	
	@Column(name = "ponto_referencia")
	private String pontoReferencia;
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}
	
	

}

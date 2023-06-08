package org.lasalle.sigas.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	

	
//	private Set<Unidade> unidades;
	
	
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
		
		System.out.println("************************************************");
		System.out.println("Unidades associadas dentro do construtor: " + usuario.getUnidades().size());
		System.out.println("*********************************************");		
	}



	public Usuario getUsuario() {
		return usuario;
	}
	
//	public Set<Unidade> getUnidades(){
//		return unidades;
//	}
	
//	public List<Unidade> getUnidades() {
//		return usuario.getUnidades();
//	}


}

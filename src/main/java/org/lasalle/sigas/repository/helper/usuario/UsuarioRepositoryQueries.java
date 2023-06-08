package org.lasalle.sigas.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepositoryQueries {
	
	public Optional<Usuario> porEmailEAtivo(String email);

	public List<String> permissoes(Usuario usuario);
	
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
	
	public Usuario buscarUsuarioComGrupos(Long id);
	
	public List<Usuario> buscarUsuarioPeloId(Long id);
	
	public Usuario buscarUsuarioPeloHash(String hash);
}

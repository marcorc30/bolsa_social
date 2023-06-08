package org.lasalle.sigas.repository;

import java.util.Optional;

import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.repository.helper.usuario.UsuarioRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQueries{
	
	public Optional<Usuario> findByCpf(String cpf);
	
	public Optional<Usuario> findByEmail(String email);
	
	public Optional<Usuario> findById(Long id);

	public Optional<Usuario> findByHash(String hash);

	
	

}

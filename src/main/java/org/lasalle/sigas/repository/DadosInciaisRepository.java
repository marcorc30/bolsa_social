package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.repository.helper.solicitacao.DadosInciaisRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosInciaisRepository extends JpaRepository<DadosIniciais, Long>, DadosInciaisRepositoryQueries{

	public DadosIniciais findById(Long id);
	
	public List<DadosIniciais> findByUsuarioId(Long usuarioId);
}

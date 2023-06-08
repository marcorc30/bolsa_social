package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.repository.helper.composicaoFamiliar.ComposicaoFamiliarRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposicaoFamiliarRepository extends 
		JpaRepository<ComposicaoFamiliar, Long>, ComposicaoFamiliarRepositoryQueries{
	
	public List<ComposicaoFamiliar> findByDadosIniciaisId(Long id);

	public ComposicaoFamiliar findById(Long id);

//	public ComposicaoFamiliar findByNome(String nome);

	public ComposicaoFamiliar findByNomeAndDadosIniciaisId(String nome, Long id);

}

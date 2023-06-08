package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.helper.unidade.UnidadeRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long>, UnidadeRepositoryQueries{
	
	public List<Unidade> findByUf(String uf);
	
//	public List<Unidade> findById(Long id);
	
	public Unidade findById(Long id);

	public Unidade findByNome(String nome); 
	
	
	

}

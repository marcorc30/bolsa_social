package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{
	
	public Grupo findById(Long id);

	
	
}

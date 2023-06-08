package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long>{

	public EstadoCivil getById(Long id);
	
	
}

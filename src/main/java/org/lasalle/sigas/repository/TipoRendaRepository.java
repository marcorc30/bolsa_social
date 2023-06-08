package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.TipoRenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRendaRepository extends JpaRepository<TipoRenda, Long>{

	
	
}

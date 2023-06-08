package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.Nacionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, Long>{

	
	
}

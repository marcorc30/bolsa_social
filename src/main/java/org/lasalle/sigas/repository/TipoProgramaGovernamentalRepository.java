package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.TipoProgramaGovernamental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProgramaGovernamentalRepository extends JpaRepository<TipoProgramaGovernamental, Long>{
	
	

}

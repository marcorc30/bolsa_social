package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.DeclaracaoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface DeclaracaoFinalRepository extends JpaRepository<DeclaracaoFinal, Long>{

	public DeclaracaoFinal findByDadosIniciaisId(Long id);

	
}

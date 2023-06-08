package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.SituacaoHabitacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoHabitacionalRepository extends JpaRepository<SituacaoHabitacional, Long>{
	
	public SituacaoHabitacional findByDadosIniciaisId(Long id);
	


}

package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>{

	List<Candidato> findByDadosIniciaisId(Long id);
	
//	Candidato findByDadosIniciaisId(Long id);

}

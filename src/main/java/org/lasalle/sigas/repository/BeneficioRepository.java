package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long>{
	
	public List<Beneficio> findByDadosIniciaisId(Long id);

	public Beneficio findById(Long id);

}

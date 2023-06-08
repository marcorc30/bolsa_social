package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.RendaAgregada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendaAgregadaRepository extends JpaRepository<RendaAgregada, Long>{

	public List<RendaAgregada> findByDadosIniciaisId(Long id);

	public RendaAgregada findById(Long id);
	
}

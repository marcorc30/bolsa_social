package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long>{

	public List<Documento> findByDadosIniciaisId(Long id);

	public Documento findById(Long id);
	
	
}

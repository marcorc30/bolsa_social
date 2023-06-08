package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.Documento;
import org.lasalle.sigas.model.DocumentoComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoComumRepository extends JpaRepository<DocumentoComum, Long>{

	public List<DocumentoComum> findByDadosIniciaisId(Long id);

	public DocumentoComum findById(Long id);
}

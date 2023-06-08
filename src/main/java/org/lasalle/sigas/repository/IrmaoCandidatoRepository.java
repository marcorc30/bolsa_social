package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.IrmaoCandidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrmaoCandidatoRepository extends JpaRepository<IrmaoCandidato, Long>{
	
	public List<IrmaoCandidato> findByDadosIniciaisId(Long id);
	
	public IrmaoCandidato findById(Long id);

}

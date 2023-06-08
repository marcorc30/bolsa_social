package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.BemMovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BemMovelRepository extends JpaRepository<BemMovel, Long>{
	
	public List<BemMovel> findByDadosIniciaisId(Long id);

	public BemMovel findById(Long id);

}

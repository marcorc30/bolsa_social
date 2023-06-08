package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.IrReposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrRespostaRepository extends JpaRepository<IrReposta, Long>{
	
	

}

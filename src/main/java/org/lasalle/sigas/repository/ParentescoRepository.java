package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.Parentesco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentescoRepository extends JpaRepository<Parentesco, Long>{

	Parentesco findById(long l);

}

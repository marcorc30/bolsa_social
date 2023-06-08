package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.Escolaridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaridadeRepository extends JpaRepository<Escolaridade, Long>{

	Escolaridade findById(long l);

}

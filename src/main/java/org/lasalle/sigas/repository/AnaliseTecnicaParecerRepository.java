package org.lasalle.sigas.repository;

import java.util.Optional;

import org.lasalle.sigas.model.AnaliseTecnicaParecer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaliseTecnicaParecerRepository extends JpaRepository<AnaliseTecnicaParecer, Long>{

	Optional<AnaliseTecnicaParecer> findById(Long id);

}

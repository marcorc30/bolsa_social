package org.lasalle.sigas.repository;

import java.util.Optional;

import org.lasalle.sigas.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long>{

	public Optional<Sede> findById(Long id);
	
}

package org.lasalle.sigas.repository;

import java.util.Optional;

import org.lasalle.sigas.model.AnaliseCIBS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaliseCIBSRepository extends JpaRepository<AnaliseCIBS, Long>{

	public Optional<AnaliseCIBS> findById(Long id);
}

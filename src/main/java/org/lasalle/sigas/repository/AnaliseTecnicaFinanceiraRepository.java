package org.lasalle.sigas.repository;

import java.util.Optional;

import org.lasalle.sigas.model.AnaliseTecnicaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaliseTecnicaFinanceiraRepository extends JpaRepository<AnaliseTecnicaFinanceira, Long>{

	Optional<AnaliseTecnicaFinanceira> findById(Long id);

}

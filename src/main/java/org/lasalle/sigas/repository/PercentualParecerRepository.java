package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.PercentualParecer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PercentualParecerRepository extends JpaRepository<PercentualParecer, Long>{

	PercentualParecer findById(Long id);

}

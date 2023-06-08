package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.TipoTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTransporteRepository extends JpaRepository<TipoTransporte, Long>{

	
}

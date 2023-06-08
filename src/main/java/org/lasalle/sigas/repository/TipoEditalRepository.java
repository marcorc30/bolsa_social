package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.TipoEdital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEditalRepository extends JpaRepository<TipoEdital, Long>{

}

package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.TipoDocumentoComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoComumRepository extends JpaRepository<TipoDocumentoComum, Long>{

}

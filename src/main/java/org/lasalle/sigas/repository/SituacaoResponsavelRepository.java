package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.SituacaoReponsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoResponsavelRepository extends JpaRepository<SituacaoReponsavel, Long>{

}

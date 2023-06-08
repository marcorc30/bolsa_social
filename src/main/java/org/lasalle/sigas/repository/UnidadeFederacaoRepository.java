package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.UnidadeFederacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeFederacaoRepository extends JpaRepository<UnidadeFederacao, Long>{

}

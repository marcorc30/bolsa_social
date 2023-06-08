package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.TipoInstituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoInstituicaoRepository extends JpaRepository<TipoInstituicao, Long>{

}

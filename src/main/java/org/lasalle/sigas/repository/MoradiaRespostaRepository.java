package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.MoradiaResposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaRespostaRepository extends JpaRepository<MoradiaResposta, Long>{

}

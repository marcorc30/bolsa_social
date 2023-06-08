package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.MoradiaCondicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaCondicaoRepository extends JpaRepository<MoradiaCondicao, Long>{

}

package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.MoradiaImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaImovelRepository extends JpaRepository<MoradiaImovel, Long>{

}

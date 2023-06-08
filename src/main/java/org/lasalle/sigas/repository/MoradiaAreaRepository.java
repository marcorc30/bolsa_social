package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.MoradiaArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaAreaRepository extends JpaRepository<MoradiaArea, Long>{

}

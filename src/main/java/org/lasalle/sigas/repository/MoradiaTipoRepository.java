package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.MoradiaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradiaTipoRepository extends JpaRepository<MoradiaTipo, Long>{

}

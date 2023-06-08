package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.AnaliseDocumental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaliseDocumentalRepository extends JpaRepository<AnaliseDocumental, Long>{

	List<AnaliseDocumental> findByDadosIniciais(Integer id);

}

package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.ComissaoInterna;
import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComissaoInternaRepository extends JpaRepository<ComissaoInterna, Long>{

	List<ComissaoInterna> findByProcessoSeletivoId(Long id);

	ComissaoInterna findById(Long id);

}

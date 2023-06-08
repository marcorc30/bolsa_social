package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	public List<Despesa> findByDadosIniciaisId(Long id);

	public Despesa findById(Long id);
}

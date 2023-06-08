package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.ResponsavelFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelFinanceiroRepository extends JpaRepository<ResponsavelFinanceiro, Long>{

	public ResponsavelFinanceiro findByDadosIniciaisId(Long id);

//	public Responsavel findByNome(String nome);
}

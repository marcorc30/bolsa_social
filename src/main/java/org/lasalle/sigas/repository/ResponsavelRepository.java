package org.lasalle.sigas.repository;

import java.util.List;
import java.util.Optional;

import org.lasalle.sigas.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long>{
	
	public List<Responsavel> findByDadosIniciaisId(Long id);

	public Responsavel findById(Long id);

	public Optional<Responsavel> findByCpfAndDadosIniciaisId(String cpf, Long id);

	public Responsavel findByNomeAndDadosIniciaisId(String nome, Long id);

}

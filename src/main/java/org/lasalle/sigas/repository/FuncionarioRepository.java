package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.Funcionario;
import org.lasalle.sigas.repository.helper.funcionario.FuncionarioRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, FuncionarioRepositoryQueries{

	
}

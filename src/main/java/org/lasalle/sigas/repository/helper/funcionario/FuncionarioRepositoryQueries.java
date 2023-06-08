package org.lasalle.sigas.repository.helper.funcionario;

import java.util.List;

import org.lasalle.sigas.model.Funcionario;

public interface FuncionarioRepositoryQueries {
	
	public List<Funcionario> funcionariosAssistente();
	
	public List<Funcionario> funcionariosPorUnidade(Long idUnidade); 

}

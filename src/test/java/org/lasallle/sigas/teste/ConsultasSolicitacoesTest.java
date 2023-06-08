package org.lasallle.sigas.teste;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceContext
public class ConsultasSolicitacoesTest {

	@PersistenceUnit
	EntityManager em;
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;
	
	
	@Test
	public void TestConsulta() throws Exception {
		

		
		
	}
	
}

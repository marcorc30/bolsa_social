package org.lasalle.sigas.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.service.exception.DatasIncompativeisException;
import org.lasalle.sigas.service.exception.ImpossivelExcluirProcessoSeletivoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessoSeletivoService {
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;
	
	@PersistenceContext
	EntityManager manager;
	
	@Transactional
	public void salvar(ProcessoSeletivo processoSeletivo) {
		
		if (processoSeletivo.getPreenchimentoCadastroInicio().isBefore(processoSeletivo.getDivulgacaoEdital())) {
			throw new DatasIncompativeisException("Data do preenchimento do Cadastro do Candidato e Upload dos Documentos não pode ser inferior a data para Divulgação do Edital da Bolsa ");
		}
		
		if (processoSeletivo.getAnalisePerfilInicio().isBefore(processoSeletivo.getPreenchimentoCadastroInicio())) {
			throw new DatasIncompativeisException("Data da Análise do perfil sócioeconômico, entrevistas e agenda para visita domiciliar não pode ser inferior a data do preenchimento do cadastro do candidato e upload dos documentos ");
		}
		
		if (processoSeletivo.getResultadoInicio().isBefore(processoSeletivo.getValidacaoComissaoInternaInicio())) {
			throw new DatasIncompativeisException("Data do Resultado dos pré-selecionados à concessão de Bolsa Social domiciliar não pode ser inferior a data da Validação Interna da Comissão de Bolsa Social ");
		}
		
		processoSeletivoRepository.save(processoSeletivo);
	}
	
	@Transactional(readOnly = true)
	public List<ProcessoSeletivo> listarPorCodigoUnidade(Long idUnidade){
		
		String jpql = "select ps from ProcessoSeletivo ps join fetch ps.unidade un where un.id = :pId";
		TypedQuery<ProcessoSeletivo> query = manager.createQuery(jpql, ProcessoSeletivo.class);
		query.setParameter("pId", idUnidade);
		
		return query.getResultList();
	}
	
	@Transactional
	public void excluir(Long id) {
		try{
			processoSeletivoRepository.delete(id);
			processoSeletivoRepository.flush();
			
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirProcessoSeletivoException("Não foi possível apagar esse Processo Seletivo. Podem haver séries ou CIBS vinculadas.");
		}
		
	}

}

package org.lasalle.sigas.repository.helper.solicitacao;

import java.util.List;

import org.lasalle.sigas.dto.SolicitacoesConsultaDTO;
import org.lasalle.sigas.dto.UnidadeTotal;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Unidade;

public interface DadosInciaisRepositoryQueries {

	public List<DadosIniciais> solicitacaoPorUsuario(Long idUsuario);
	
	public List<SolicitacoesConsultaDTO> solicitacaoPorProcessoSeletivoConsultaNativa(Long idProcesso);
	
	public List<SolicitacoesConsultaDTO> solicitacaoPorProcessoSeletivoConsultaNativaPorStatus(Long idProcesso, String status, 
			Boolean ehComissao, String nome, String protocolo, String ordem);
	
	
	public List<DadosIniciais> solicitacaoPorIdUsuario(Long idUsuario);
	
	public List<UnidadeTotal> totalPorUnidade();

	public List<UnidadeTotal> totalPorUnidade(List<Unidade> unidades);
	
}

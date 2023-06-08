package org.lasalle.sigas.controller;

import java.text.DecimalFormat;
import java.util.Optional;

import org.lasalle.sigas.model.AnaliseTecnicaFinanceira;
import org.lasalle.sigas.model.AnaliseTecnicaParecer;
import org.lasalle.sigas.model.Candidato;
import org.lasalle.sigas.repository.AnaliseDocumentalRepository;
import org.lasalle.sigas.repository.AnaliseTecnicaFinanceiraRepository;
import org.lasalle.sigas.repository.AnaliseTecnicaParecerRepository;
import org.lasalle.sigas.repository.BemMovelRepository;
import org.lasalle.sigas.repository.BeneficioRepository;
import org.lasalle.sigas.repository.CandidatoRepository;
import org.lasalle.sigas.repository.ComposicaoFamiliarRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DeclaracaoFinalRepository;
import org.lasalle.sigas.repository.DespesaRepository;
import org.lasalle.sigas.repository.DocumentoComumRepository;
import org.lasalle.sigas.repository.DocumentoRepository;
import org.lasalle.sigas.repository.IrmaoCandidatoRepository;
import org.lasalle.sigas.repository.PercentualParecerRepository;
import org.lasalle.sigas.repository.RendaAgregadaRepository;
import org.lasalle.sigas.repository.ResponsavelFinanceiroRepository;
import org.lasalle.sigas.repository.ResponsavelRepository;
import org.lasalle.sigas.repository.SituacaoHabitacionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/analise")
public class AnaliseController {
	
	@Autowired 
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	CandidatoRepository candidatoRepository;
	
	@Autowired
	IrmaoCandidatoRepository irmaoCandidatoRepository;
	
	@Autowired
	ResponsavelRepository responsavelRepository;
	
	@Autowired
	ComposicaoFamiliarRepository composicaoFamiliarRepository;
	
	@Autowired
	ResponsavelFinanceiroRepository responsavelFinanceiroRepository;
	
	@Autowired
	SituacaoHabitacionalRepository situacaoHabitacionalRepository;
	
	@Autowired
	BemMovelRepository bemMovelRepository;
	
	@Autowired
	DespesaRepository despesaRepository;
	
	@Autowired
	RendaAgregadaRepository rendaAgregadaRepository;
	
	@Autowired
	BeneficioRepository beneficioRepository;
	
	@Autowired
	DeclaracaoFinalRepository declaracaoFinalRepository;
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	DocumentoComumRepository documentoComumRepository;
	
	@Autowired
	PercentualParecerRepository percentualParecerRepository;
	
	@Autowired
	AnaliseTecnicaFinanceiraRepository analiseTecnicaFinanceiraRepository;
	
	@Autowired
	AnaliseTecnicaParecerRepository analiseTecnicaParecerRepository;
	
	@Autowired
	AnaliseDocumentalRepository analiseDocumentalRepository;
	
	
	
	@RequestMapping("/{id}")
	public ModelAndView analisar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/solicitacao/AnalisarSolicitacao");
		
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(id).get(0);
		
		mv.addObject("dadosIniciais", dadosIniciaisRepository.findById(id));
		mv.addObject("candidato", candidato);
		mv.addObject("irmaos", irmaoCandidatoRepository.findByDadosIniciaisId(id));
		mv.addObject("responsaveis", responsavelRepository.findByDadosIniciaisId(id));
		mv.addObject("composicoes", composicaoFamiliarRepository.findByDadosIniciaisId(id));
		mv.addObject("responsavelFinanceiro", responsavelFinanceiroRepository.findByDadosIniciaisId(id));
		mv.addObject("situacaoHabitacional", situacaoHabitacionalRepository.findByDadosIniciaisId(id));
		mv.addObject("bensMoveis", bemMovelRepository.findByDadosIniciaisId(id));
		mv.addObject("despesas", despesaRepository.findByDadosIniciaisId(id));
		mv.addObject("rendas", rendaAgregadaRepository.findByDadosIniciaisId(id));
		mv.addObject("beneficios", beneficioRepository.findByDadosIniciaisId(id));
		mv.addObject("declaracaoFinal", declaracaoFinalRepository.findByDadosIniciaisId(id));
		mv.addObject("documentos", documentoComumRepository.findByDadosIniciaisId(id));
		mv.addObject("percentuais", percentualParecerRepository.findAll());
		mv.addObject("analisesDocumentais", analiseDocumentalRepository.findByDadosIniciais(Integer.valueOf(id.toString())));
		
		
		Optional<AnaliseTecnicaFinanceira> analiseTecnicaFinanceira = analiseTecnicaFinanceiraRepository.findById(id);
		Optional<AnaliseTecnicaParecer> analiseTecnicaParecer = analiseTecnicaParecerRepository.findById(id);
		
		if (analiseTecnicaParecer.isPresent()) {
			mv.addObject("analiseTecnicaParecer", analiseTecnicaParecer.get());
		}else {
			AnaliseTecnicaParecer analiseTecnicaParecerNovo = new AnaliseTecnicaParecer();
			mv.addObject("analiseTecnicaParecer", analiseTecnicaParecerNovo);
		}
		
		if (analiseTecnicaFinanceira.isPresent()) {
			mv.addObject("analiseTecnicaFinanceira", analiseTecnicaFinanceira.get());
			mv.addObject("percentualBolsa", analiseTecnicaFinanceira.get().isTemPercentual());
		}else {
			AnaliseTecnicaFinanceira analiseTecnicaFinanceiraNovo = new AnaliseTecnicaFinanceira();
			mv.addObject("analiseTecnicaFinanceira", analiseTecnicaFinanceiraNovo);
		}
		
		
		
		
		
		
		// calculando o total da renda
		Double renda = composicaoFamiliarRepository
						.findByDadosIniciaisId(id)
						.stream()
						.mapToDouble(c -> c.getSalario().doubleValue())
						.sum();
				
		
		// calculando a quantidade de componentes familiares
		long total = composicaoFamiliarRepository
						.findByDadosIniciaisId(id)
						.stream()
						.mapToInt(c -> c.getSalario().intValue())
						.count();
		
		// calculando a renda per capta
		Double rendaPerCapta = composicaoFamiliarRepository
								.findByDadosIniciaisId(id)
								.stream()
								.mapToDouble(c -> c.getSalario().doubleValue())
								.average().getAsDouble();

		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		
		mv.addObject("renda", df.format(renda));
		mv.addObject("total", total);
		mv.addObject("rendaPerCapta", df.format(rendaPerCapta));
		
		
		return mv;
		
	}
	


}

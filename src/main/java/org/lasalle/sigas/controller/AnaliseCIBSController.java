package org.lasalle.sigas.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.lasalle.sigas.model.AnaliseCIBS;
import org.lasalle.sigas.model.Candidato;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.repository.AnaliseCIBSRepository;
import org.lasalle.sigas.repository.AnaliseTecnicaFinanceiraRepository;
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
import org.lasalle.sigas.service.AnaliseCIBSService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/analise-cibs")
public class AnaliseCIBSController {

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
	AnaliseCIBSService analiseCIBSService;
	
	@Autowired
	AnaliseCIBSRepository analiseCIBSRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;	
	
	@RequestMapping("/{id}")
	public ModelAndView novo(@PathVariable("id") Long id, AnaliseCIBS analiseCIBS) {
		ModelAndView mv = new ModelAndView("/solicitacao/AnaliseCIBS");
		
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(id).get(0);
		
		mv.addObject("dadosIniciais", dadosIniciaisRepository.findById(id));
		mv.addObject("candidato", candidato);
		mv.addObject("percentuais", percentualParecerRepository.findAll());
		mv.addObject("analiseTecnicaFinanceira", analiseTecnicaFinanceiraRepository.findById(id).get());
		mv.addObject("beneficios", beneficioRepository.findByDadosIniciaisId(id));
		
		Optional<AnaliseCIBS> analiseCIBSOptional = analiseCIBSRepository.findById(id);
		
		if (analiseCIBSOptional.isPresent()) {
			mv.addObject("analiseCIBS", analiseCIBSOptional.get());
		}
		
	
		
		return mv;
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Valid AnaliseCIBS analiseCIBS,  BindingResult result, RedirectAttributes redirectAttributes) {
		
		
		if (result.hasErrors()) {
			return novo(analiseCIBS.getId(), analiseCIBS);
		}
		
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(analiseCIBS.getId()).get(0);
		
		redirectAttributes.addFlashAttribute("dadosIniciais", dadosIniciaisRepository.findById(analiseCIBS.getId()));
		redirectAttributes.addFlashAttribute("candidato", candidato);
		redirectAttributes.addFlashAttribute("percentuais", percentualParecerRepository.findAll());
		redirectAttributes.addFlashAttribute("analiseTecnicaFinanceira", analiseTecnicaFinanceiraRepository.findById(analiseCIBS.getId()));
		redirectAttributes.addFlashAttribute("beneficios", beneficioRepository.findByDadosIniciaisId(analiseCIBS.getId()));
		
		DadosIniciais dadosIniciaisObtido = dadosIniciaisRepository.findById(analiseCIBS.getId());
		dadosIniciaisObtido.setStatus(StatusSolicitacao.FINALIZADO);
		dadosIniciaisService.salvar(dadosIniciaisObtido);		
		
		
		analiseCIBS.setDadosIniciais(dadosIniciaisRepository.findById(analiseCIBS.getId()));
		analiseCIBSService.salvar(analiseCIBS);
		redirectAttributes.addFlashAttribute("analiseCIBS", analiseCIBS);
		redirectAttributes.addFlashAttribute("mensagem", "Analise cadastrada com sucesso. Botão de impressão do Parecer CIBS foi habilitado.");
		
		return new ModelAndView("redirect:/analise-cibs/" + analiseCIBS.getId());
		
	}
	
}

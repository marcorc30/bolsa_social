package org.lasalle.sigas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.Candidato;
import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.lasalle.sigas.model.Escolaridade;
import org.lasalle.sigas.model.EstadoCivil;
import org.lasalle.sigas.model.InstituicaoOrigem;
import org.lasalle.sigas.model.Parentesco;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Sexo;
import org.lasalle.sigas.model.TipoInstituicao;
import org.lasalle.sigas.model.Turno;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.BolsaAnteriorRespostaRepository;
import org.lasalle.sigas.repository.ComposicaoFamiliarRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DetalhesProcessoSeletivoRepository;
import org.lasalle.sigas.repository.EscolaridadeRepository;
import org.lasalle.sigas.repository.EstadoCivilRepository;
import org.lasalle.sigas.repository.MunicipioRepository;
import org.lasalle.sigas.repository.NacionalidadeRepository;
import org.lasalle.sigas.repository.ParentescoRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.TipoInstituicaoRepository;
import org.lasalle.sigas.repository.TipoTransporteRepository;
import org.lasalle.sigas.repository.TurnoRepository;
import org.lasalle.sigas.repository.UnidadeFederacaoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.service.CandidatoService;
import org.lasalle.sigas.service.ComposicaoFamiliarService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.exception.CandidatoSemLaudoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidatos")
public class CandidatoController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;

	@Autowired
	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
	@Autowired
	TipoInstituicaoRepository tipoInstituicaoRepository;
	
	@Autowired
	NacionalidadeRepository nacionalidadeRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	UnidadeFederacaoRepository unidadeFederacaoRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	TipoTransporteRepository tipoTransporteRepository;
	
	@Autowired
	CandidatoService candidatoService;
	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	ComposicaoFamiliarService composicaoFamiliarService;
	
	@Autowired
	EstadoCivilRepository estadoCivilRepository;
	
	@Autowired
	ParentescoRepository parentescoRepository;
	
	@Autowired
	EscolaridadeRepository escolaridadeRepository;
	
	@Autowired
	MunicipioRepository municipioRepository;
	
	@Autowired
	BolsaAnteriorRespostaRepository bolsaAnteriorRespostaRepository;
	

	
	@RequestMapping("/novo")
	public String novo(Candidato candidato, Model model) { 
		
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository
												.findById(this.dadosIniciais
														.getProcessoSeletivo().getId());
		
		Long anoAnterior = processoSeletivo.getAno() - 1;
		
		Unidade unidade = unidadeRepository.findById(this.dadosIniciais.getUnidade().getId());
		
		DetalhesProcessoSeletivo detalhesProcesso = detalhesProcessoSeletivoRepository.findById(this.dadosIniciais.getDetalhesProcessoSeletivo().getId());
				
		model.addAttribute("sexos", Sexo.values());
		model.addAttribute("instituicoes", tipoInstituicaoRepository.findAll());
		model.addAttribute("nacionalidades", nacionalidadeRepository.findAll());
		model.addAttribute("turnos", turnoRepository.findAll());
		model.addAttribute("processoSeletivo", processoSeletivo);
		model.addAttribute("unidade", unidade);
		model.addAttribute("detalhesProcesso", detalhesProcesso);
		model.addAttribute("ufs", unidadeFederacaoRepository.findAll());
		model.addAttribute("protocolo", this.dadosIniciais.getProtocolo());
		model.addAttribute("tiposTransporte", tipoTransporteRepository.findAll());
		model.addAttribute("municipios", municipioRepository.findAll());
		model.addAttribute("respostasBolsaAnterior", bolsaAnteriorRespostaRepository.findAll());
		model.addAttribute("anoAnterior", anoAnterior);
		
		return "candidato/NovoCandidato";
	}
	
	
	
	@RequestMapping(value="/novo", method = RequestMethod.POST)
	public String salvar(@Valid Candidato candidato, BindingResult result, RedirectAttributes attributes, Model model) {
		
		boolean possuiIrmaos = candidato.getPossuiIrmaosColegio();
		
		if (result.hasErrors()) {
			return novo(candidato, model);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa3(true);
		
		if (!possuiIrmaos) {
			dadosIniciais.setEtapa4(true);
		}
		
		try {
			dadosIniciaisService.salvar(dadosIniciais);
			
			candidato.setDadosIniciais(dadosIniciais);
			candidato = candidatoService.salvar(candidato);
			
		}catch(CandidatoSemLaudoException e) {
			result.rejectValue("possuiDeficiencia", e.getMessage(), e.getMessage());
			return novo(candidato, model);
		}
				

		cadastrarCandidatoTabelaComponenteFamiliar(candidato, dadosIniciais);
		
		this.dadosIniciais.setCandidato(candidato.getNome());
		
		if (possuiIrmaos) {
			return "redirect:/irmaos/novo";
			
		}else {
			return "redirect:/responsaveis/novo";
		}
		
	}
	
	@RequestMapping("/pesquisarCandidato")
	public String listarCandidatos() {
	
		return "candidato/PesquisaCandidato";
	}
	
	public Integer calcularIdade(LocalDate dataNascimento) {
		LocalDate aniversario = dataNascimento;
	    LocalDate dataAtual = LocalDate.now();
	    Period periodo = Period.between(aniversario, dataAtual);
	    return periodo.getYears();		
	}

	public void cadastrarCandidatoTabelaComponenteFamiliar(Candidato candidato, DadosIniciais dadosIniciais) {
		
		EstadoCivil estadoCivil = estadoCivilRepository.getById(4l);
		Parentesco parentesco = parentescoRepository.findById(21l);
		Escolaridade escolaridade = escolaridadeRepository.findById(9l);
		
		ComposicaoFamiliar composicao = new ComposicaoFamiliar();
		composicao.setNome(candidato.getNome());
		composicao.setParentesco(parentesco);
		composicao.setDataNascimento(candidato.getDataNascimento());
		composicao.setIdade(calcularIdade(candidato.getDataNascimento()));
		composicao.setEstadoCivil(estadoCivil);
		composicao.setEscolaridade(escolaridade);
		composicao.setOcupacao("Estudante");
		composicao.setDadosIniciais(dadosIniciais);
		composicao.setSalario(BigDecimal.ZERO);
		composicao.setCpf(candidato.getCpf());
		composicao.setConferido(true);
		
		composicaoFamiliarService.salvar(composicao);
		
		
		
	}
	
	
	
	
}

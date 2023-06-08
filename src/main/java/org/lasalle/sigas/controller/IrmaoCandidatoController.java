package org.lasalle.sigas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.lasalle.sigas.model.Escolaridade;
import org.lasalle.sigas.model.EstadoCivil;
import org.lasalle.sigas.model.IrmaoCandidato;
import org.lasalle.sigas.model.Parentesco;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DetalhesProcessoSeletivoRepository;
import org.lasalle.sigas.repository.EscolaridadeRepository;
import org.lasalle.sigas.repository.EstadoCivilRepository;
import org.lasalle.sigas.repository.IrmaoCandidatoRepository;
import org.lasalle.sigas.repository.ParentescoRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.service.ComposicaoFamiliarService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.IrmaoCandidatoService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/irmaos")
public class IrmaoCandidatoController {
	
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
//	@Autowired
//	UnidadeRepository unidadeRepository;
//	
//	@Autowired
//	ProcessoSeletivoRepository processoSeletivoRepository;
//
//	@Autowired
//	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
//	@Autowired
//	UnidadeFederacaoRepository unidadeFederacaoRepository;
//	
//	@Autowired
//	CandidatoRepository candidatoRepository;
	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	IrmaoCandidatoService irmaoCandidatoService;

	@Autowired
	IrmaoCandidatoRepository irmaoCandidatoRepository;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;

	@Autowired
	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
	@Autowired
	ComposicaoFamiliarService composicaoFamiliarService;
	
	@Autowired
	EstadoCivilRepository estadoCivilRepository;
	
	@Autowired
	ParentescoRepository parentescoRepository;
	
	@Autowired
	EscolaridadeRepository escolaridadeRepository;	
	
	
	
	@RequestMapping(value="novo", method = RequestMethod.GET)
	public ModelAndView novoIrmao(IrmaoCandidato irmaoCandidato) {
		
		ModelAndView mv = new ModelAndView("/irmaosCandidato/IrmaoCandidato");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		if (!ds.getEtapa3()) {
			return new ModelAndView("/EtapaInvalida");
		}
				
		System.out.println("dentro do controller dos irmãos " + this.dadosIniciais.getId() );
		
		
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository
				.findById(this.dadosIniciais
						.getProcessoSeletivo().getId());

		Unidade unidade = unidadeRepository.findById(this.dadosIniciais.getUnidade().getId());

		DetalhesProcessoSeletivo detalhesProcesso = detalhesProcessoSeletivoRepository.findById(this.dadosIniciais.getDetalhesProcessoSeletivo().getId());
		

		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		mv.addObject("processoSeletivo", processoSeletivo);
		mv.addObject("unidade", unidade);
		
		mv.addObject("detalhesProcesso", detalhesProcesso);
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());

		
		List<IrmaoCandidato> irmaos = irmaoCandidatoRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		
		mv.addObject("irmaos", irmaos);
		
		return mv;

	}
	
	@RequestMapping(value={"novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid IrmaoCandidato irmaoCandidato, 
			BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/irmaos/novo");
		
		String mensagem;
		
		if (result.hasErrors()) {
			return novoIrmao(irmaoCandidato);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa4(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		
		irmaoCandidato.setDadosIniciais(dadosIniciais);
		
		irmaoCandidatoService.salvar(irmaoCandidato);

//		if (irmaoCandidato.getMoraComCandidato()) {
//			cadastrarIrmaoTabelaComponenteFamiliar(irmaoCandidato, dadosIniciais);
//		}
		
		mensagem = "Irmão cadastrado com sucesso";
		
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("/irmaosCandidato/IrmaoCandidato");
		IrmaoCandidato irmaoCandidato = irmaoCandidatoRepository.findById(id);
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		
		List<IrmaoCandidato> irmaos = irmaoCandidatoRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		mv.addObject("irmaos", irmaos);
		
		mv.addObject(irmaoCandidato);
		
		if (!irmaos.contains(irmaoCandidato)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			irmaoCandidatoService.excluir(id);
			
		} catch (ImpossivelExcluirRegistro e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
		
		return ResponseEntity.ok().build();
		
		
	}

	
	public void cadastrarIrmaoTabelaComponenteFamiliar(IrmaoCandidato irmaoCandidato, DadosIniciais dadosIniciais) {
		
		EstadoCivil estadoCivil = estadoCivilRepository.getById(4l);
		Parentesco parentesco = parentescoRepository.findById(8l);
		Escolaridade escolaridade = escolaridadeRepository.findById(9l);
		
		ComposicaoFamiliar composicao = new ComposicaoFamiliar();
		composicao.setNome(irmaoCandidato.getNome());
		composicao.setParentesco(parentesco);
		composicao.setDataNascimento(LocalDate.now());
		composicao.setIdade(0);
		composicao.setEstadoCivil(estadoCivil);
		composicao.setEscolaridade(escolaridade);
		composicao.setOcupacao("Estudante");
		composicao.setDadosIniciais(dadosIniciais);
		composicao.setSalario(BigDecimal.ZERO);
		composicao.setConferido(false);
		
		composicaoFamiliarService.salvar(composicao);
		
	}
	
	@RequestMapping("/direcionar")
	public ModelAndView direcionar() {
		
		List<IrmaoCandidato> irmaos = irmaoCandidatoRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		irmaos.forEach(irmao -> {
			if (irmao.getMoraComCandidato()) {
				cadastrarIrmaoTabelaComponenteFamiliar(irmao, dadosIniciais);
			}			
		});
		
		
//		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa4(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		return new ModelAndView("redirect:/responsaveis/novo");
		
	}
	
	
}

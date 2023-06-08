package org.lasalle.sigas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Escolaridade;
import org.lasalle.sigas.model.EstadoCivil;
import org.lasalle.sigas.model.IrmaoCandidato;
import org.lasalle.sigas.model.Parentesco;
import org.lasalle.sigas.model.Responsavel;
import org.lasalle.sigas.model.SituacaoReponsavel;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DetalhesProcessoSeletivoRepository;
import org.lasalle.sigas.repository.EscolaridadeRepository;
import org.lasalle.sigas.repository.EstadoCivilRepository;
import org.lasalle.sigas.repository.ParentescoRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.ResponsavelRepository;
import org.lasalle.sigas.repository.SituacaoResponsavelRepository;
import org.lasalle.sigas.repository.UnidadeFederacaoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.service.ComposicaoFamiliarService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.ResponsavelService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/responsaveis")
public class ResponsavelController {

	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;

	@Autowired
	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
	@Autowired
	UnidadeFederacaoRepository unidadeFederacaoRepository;
	
	@Autowired
	SituacaoResponsavelRepository situacaoResponsavelRepository;
	
	@Autowired
	ParentescoRepository parentescoRepository;
	
	@Autowired
	ResponsavelService responsavelService;
	
	@Autowired
	DadosInciaisRepository dadosIniciasRepository;
	
	@Autowired
	ResponsavelRepository responsavelRepository;
		
	@Autowired
	ComposicaoFamiliarService composicaoFamiliarService;
	
	@Autowired
	EstadoCivilRepository estadoCivilRepository;
	
	@Autowired
	EscolaridadeRepository escolaridadeRepository;		
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(Responsavel responsavel) {
		
		ModelAndView mv = new ModelAndView("/responsavel/NovoResponsavel");
		
		DadosIniciais ds = dadosIniciasRepository.findById(this.dadosIniciais.getId());
		
		if (!ds.getEtapa3() || !ds.getEtapa4()) {
			return new ModelAndView("/EtapaInvalida");
		}
		
		
		
		List<Responsavel> responsaveis = responsavelRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		
		
		List<Parentesco> parentescos = parentescoRepository.findAll();
		List<SituacaoReponsavel> situacoesResponsavel = situacaoResponsavelRepository.findAll();
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		mv.addObject("parentescos", parentescos);
		mv.addObject("situacoesResponsavel", situacoesResponsavel);
		mv.addObject("responsaveis", responsaveis);
		
		return mv;
	}
	
	@RequestMapping(value={"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Responsavel responsavel, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(responsavel);
		}
		
		DadosIniciais dadosIniciais = dadosIniciasRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa5(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		
		responsavel.setDadosIniciais(dadosIniciais);
		responsavelService.salvar(responsavel);

		if (responsavel.getSituacaoResponsavel().getId() == 1) {
			cadastrarResponsavelTabelaComponenteFamiliar(responsavel, dadosIniciais);
		}
		
		return new ModelAndView("redirect:/responsaveis/novo");
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/responsavel/NovoResponsavel");
	

		Responsavel responsavel = responsavelRepository.findById(id);
		
		List<Responsavel> responsaveis = responsavelRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		List<Parentesco> parentescos = parentescoRepository.findAll();
		List<SituacaoReponsavel> situacoesResponsavel = situacaoResponsavelRepository.findAll();
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		mv.addObject("parentescos", parentescos);
		mv.addObject("situacoesResponsavel", situacoesResponsavel);
		mv.addObject("responsaveis", responsaveis);
		
		mv.addObject(responsavel);
		
		if (!responsaveis.contains(responsavel)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		
		return mv;
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			responsavelService.excluir(id);
		} catch (ImpossivelExcluirRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
		return ResponseEntity.ok().build();
		
	}
	
	@RequestMapping(value="/pesquisar", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> buscarPeloCpf(String cpf){
		
		Optional<Responsavel> responsavel = responsavelRepository.findByCpfAndDadosIniciaisId(cpf, this.dadosIniciais.getId());
		
		if (!responsavel.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		Responsavel resp = new Responsavel();
		resp.setDataNascimento(responsavel.get().getDataNascimento());
		resp.setNome(responsavel.get().getNome());
		
		return ResponseEntity.ok(resp); 
		
	}
	
	@RequestMapping(value="/pesquisar-nome", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> componentesPorNome(String nome){
		
		Responsavel rs  = responsavelRepository.findByNomeAndDadosIniciaisId(nome, this.dadosIniciais.getId());
		
		Responsavel novoresp = new Responsavel();
		novoresp.setNome(rs.getNome());
		novoresp.setDataNascimento(rs.getDataNascimento());
		novoresp.setCelular(rs.getCelular());
		novoresp.setRg(rs.getRg());
		novoresp.setRgDataEmissao(rs.getRgDataEmissao());
		novoresp.setRgOrgao(rs.getRgOrgao());
		novoresp.setCpf(rs.getCpf());
		
		
		
		
		return ResponseEntity.ok(novoresp);
		
	}
	
public void cadastrarResponsavelTabelaComponenteFamiliar(Responsavel responsavel, DadosIniciais dadosIniciais) {
		
		EstadoCivil estadoCivil = estadoCivilRepository.getById(6l);
//		Parentesco parentesco = parentescoRepository.findById(8l);
		Escolaridade escolaridade = escolaridadeRepository.findById(9l);
		
		ComposicaoFamiliar composicao = new ComposicaoFamiliar();
		composicao.setNome(responsavel.getNome());
		composicao.setParentesco(responsavel.getParentesco());
		composicao.setDataNascimento(responsavel.getDataNascimento());
		composicao.setIdade(0);
		composicao.setEstadoCivil(estadoCivil);
		composicao.setEscolaridade(escolaridade);
		composicao.setOcupacao("Outros");
		composicao.setDadosIniciais(dadosIniciais);
		composicao.setSalario(BigDecimal.ZERO);
		composicao.setConferido(false);
		
		composicaoFamiliarService.salvar(composicao);
		
	}
	
	
	
}
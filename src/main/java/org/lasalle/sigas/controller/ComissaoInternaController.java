package org.lasalle.sigas.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.lasalle.sigas.model.ComissaoInterna;
import org.lasalle.sigas.model.Funcionario;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.ComissaoInternaRepository;
import org.lasalle.sigas.repository.FuncaoRepository;
import org.lasalle.sigas.repository.FuncionarioRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.service.ComissaoInternaService;
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
@RequestMapping("/comissao")
public class ComissaoInternaController {
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@Autowired
	FuncaoRepository funcaoRepository;
	
	@Autowired
	ComissaoInternaService comissaoInternaService;
	
	@Autowired
	ComissaoInternaRepository comissaoInternaRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	
	@RequestMapping("/novo/processo/{id}")
	public ModelAndView novo(ComissaoInterna comissaoInterna, @PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("/comissao/NovaComissaoInterna");
		try {
			ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(id);
			comissaoInterna.setProcessoSeletivo(processoSeletivo);
			
			//Lista de funcionarios da unidade
			Unidade unidade = unidadeRepository.funcionariosDaUnidade(processoSeletivo.getUnidade().getId());
			List<ComissaoInterna> lista = 
					comissaoInternaRepository.findByProcessoSeletivoId(id);		
			
			List<Funcionario> funcionarios = funcionarioRepository.funcionariosPorUnidade(unidade.getId());
			
			
			mv.addObject("funcionarios", funcionarios);
			mv.addObject("processoSeletivoId", processoSeletivo);
			mv.addObject("funcoes", funcaoRepository.findAll());
			mv.addObject("comissoes", lista);
			
		}catch(NoResultException e) {
			return new ModelAndView("/ErroFuncionario");
		}
//		
		
//		mv.addObject("processoSeletivoId", processoSeletivo);
		
		return mv;
	}
	
	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid ComissaoInterna comissaoInterna,
			BindingResult result, RedirectAttributes attributes) {
		
		
		Long idProcessoSeletivo = comissaoInterna.getProcessoSeletivo().getId();
		
		System.out.println("Id do processo Seletivo dentro de salvar " + idProcessoSeletivo);
		
		if (result.hasErrors()) {
			return novo(comissaoInterna,idProcessoSeletivo);
		}
		
		try {
			comissaoInternaService.salvar(comissaoInterna);
			
		}catch (RuntimeException e) {
			
//			result.rejectValue("id", e.getMessage(), e.getMessage());
		//	result.rejectValue("serie.descricao", e.getMessage(), e.getMessage());
			return novo(comissaoInterna, idProcessoSeletivo);
		}
		
		List<ComissaoInterna> lista = 
				comissaoInternaRepository.findByProcessoSeletivoId(comissaoInterna.getProcessoSeletivo().getId());
		
		String mensagem = "Integrante da Comissão Interna do Processo Seletivo cadastrado com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
		attributes.addFlashAttribute("comissoes", lista); 
		
		return new ModelAndView("redirect:/comissao/novo/processo/" + idProcessoSeletivo);
		
	}
	
	@RequestMapping("/{id}/processo/{idProcesso}")
	public ModelAndView editar(@PathVariable("id") Long id, @PathVariable("idProcesso") Long idProcesso) {
		
		ModelAndView mv = new ModelAndView("/comissao/NovaComissaoInterna");
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(idProcesso);
		ComissaoInterna comissaoInterna = comissaoInternaRepository.findById(id);
		comissaoInterna.setProcessoSeletivo(processoSeletivo);
		
		
		List<ComissaoInterna> lista = 
				comissaoInternaRepository.findByProcessoSeletivoId(idProcesso);		
		Unidade unidade = unidadeRepository.funcionariosDaUnidade(processoSeletivo.getUnidade().getId());
		//detalhesProcessoSeletivo.getProcessoSeletivo().setId(id);
						
		mv.addObject("funcionarios", unidade.getFuncionarios());
		mv.addObject("processoSeletivoId", processoSeletivo);
		mv.addObject("funcoes", funcaoRepository.findAll());
		mv.addObject("comissoes", lista);
		mv.addObject(comissaoInterna);
		
		return mv;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			comissaoInternaService.excluir(id);
		} catch (ImpossivelExcluirRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
		return ResponseEntity.ok().build();
		
	}
		

}

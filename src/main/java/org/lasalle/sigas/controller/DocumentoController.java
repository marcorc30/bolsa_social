package org.lasalle.sigas.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Documento;
import org.lasalle.sigas.repository.ComposicaoFamiliarRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DocumentoRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.DocumentoService;
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
@RequestMapping("/documentos")
public class DocumentoController {
	
	@Resource(name = "dadosIniciais")
	DadosIniciais dadosIniciais;
	
	@Autowired
	ComposicaoFamiliarRepository composicaoFamiliarRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;

	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@RequestMapping(value="/novo", method = RequestMethod.GET)
	public ModelAndView novo(Documento documento) {
		
		ModelAndView mv = new ModelAndView("/documentos/Documentos");
		
		List<ComposicaoFamiliar> componentesFamiliares = composicaoFamiliarRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		List<Documento> documentos = documentoRepository.findByDadosIniciaisId(this.dadosIniciais.getId());

		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		

		
		mv.addObject("componentes", componentesFamiliares);
		mv.addObject("documentos", documentos);
		
		return mv;
		
	}
	
	@RequestMapping(value={"/novo" , "{\\d+}" },method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Documento documento, BindingResult result, RedirectAttributes attributes ) {
	
		if (result.hasErrors()) {
			return novo(documento);
		}
				
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setConcluido(true);
//		dadosIniciais.setEtapa14(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		documento.setDadosIniciais(dadosIniciais);
		
		documentoService.salvar(documento);
		
		return new ModelAndView("redirect:/documentos/novo");
		
	}
	
	@RequestMapping(value =  "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id) {
		
		try {
			documentoService.excluir(id);
		}catch(ImpossivelExcluirRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET )
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("/documentos/Documentos");
		
		List<ComposicaoFamiliar> componentesFamiliares = composicaoFamiliarRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		List<Documento> documentos = documentoRepository.findByDadosIniciaisId(this.dadosIniciais.getId());

		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		

		
		mv.addObject("componentes", componentesFamiliares);
		mv.addObject("documentos", documentos);
		
		
		Documento documento = documentoRepository.findById(id);
		
		mv.addObject("documento", documento);
		mv.addObject("documentos", documentos);
		
		return mv;
		
		
		
	}
	
}

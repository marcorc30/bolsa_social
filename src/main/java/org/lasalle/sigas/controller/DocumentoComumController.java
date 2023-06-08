package org.lasalle.sigas.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Documento;
import org.lasalle.sigas.model.DocumentoComum;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DocumentoComumRepository;
import org.lasalle.sigas.repository.TipoDocumentoComumRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.DocumentoComumService;
import org.lasalle.sigas.service.DocumentoService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/documentos-comuns")
public class DocumentoComumController {
	
	@Resource(name = "dadosIniciais")
	DadosIniciais dadosIniciais;
	
	@Autowired
	TipoDocumentoComumRepository tipoDocumentoComumRepository;
	
	@Autowired
	DocumentoComumRepository documentoComumRepository;
	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
		
	@Autowired
	DadosIniciaisService dadosIniciaisService;

	@Autowired
	DocumentoComumService documentoComumService;	
	
	@RequestMapping(value="/novo", method = RequestMethod.GET)
	public ModelAndView novo(DocumentoComum documentoComum) {
		ModelAndView mv = new ModelAndView("documentos/DocumentosComuns");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		if (!ds.getEtapa3() || 
				!ds.getEtapa4() || 
				!ds.getEtapa5() ||
				!ds.getEtapa6() ||
				!ds.getEtapa7() ||
				!ds.getEtapa8() ||
				!ds.getEtapa9() ||
				!ds.getEtapa10()||
				!ds.getEtapa11()||
				!ds.getEtapa12()||
				!ds.getEtapa13()) {
				return new ModelAndView("/EtapaInvalida");
			}		
		
		
		List<DocumentoComum> documentosComuns = documentoComumRepository.findByDadosIniciaisId(this.dadosIniciais.getId());

		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		
		mv.addObject("tipoDocumentosComuns", tipoDocumentoComumRepository.findAll());
		mv.addObject("documentosComuns", documentosComuns);
		
		return mv;
	}
	
	@RequestMapping(value={"/novo" , "{\\d+}" },method = RequestMethod.POST)
	public ModelAndView salvar(@Valid DocumentoComum documentoComum, BindingResult result, RedirectAttributes attributes ) {
	
		if (result.hasErrors()) {
			return novo(documentoComum);
		}
				
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
//		dadosIniciais.setEtapa14(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		
		
		documentoComum.setDadosIniciais(dadosIniciais);
		
		documentoComumService.salvar(documentoComum);
		
		return new ModelAndView("redirect:/documentos-comuns/novo");
		
	}
	
	@RequestMapping(value =  "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id) {
		
		try {
			documentoComumService.excluir(id);
		}catch(ImpossivelExcluirRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET )
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("documentos/DocumentosComuns");
		List<DocumentoComum> documentosComuns = documentoComumRepository.findByDadosIniciaisId(this.dadosIniciais.getId());

		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		
		mv.addObject("tipoDocumentosComuns", tipoDocumentoComumRepository.findAll());
		mv.addObject("documentosComuns", documentosComuns);
		
		DocumentoComum documentoComum = documentoComumRepository.findById(id);
		
		if (!documentosComuns.contains(documentoComum)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		mv.addObject("documentoComum", documentoComum);

		
		return mv;
		
		
		
	}
	
	@RequestMapping("/finalizar")
	public ModelAndView finalizar(DocumentoComum documentoComum, BindingResult result,  RedirectAttributes attributes) {
		
		List<DocumentoComum> documentosComuns = documentoComumRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		
		if (documentosComuns.size() < 1) {
			result.reject("Você não pode finalizar essa etapa sem antes incluir ao menos 1 (um) documento.","Você não pode finalizar essa etapa sem antes incluir ao menos 1 (um) documento.");
			return novo(documentoComum);
		}
		
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa14(true);
		dadosIniciais.setConcluido(true);
		
		if (concluido(dadosIniciais)) {
			if (dadosIniciais.getStatus() == StatusSolicitacao.PENDENCIA_DOCUMENTOS) {
				dadosIniciais.setStatus(StatusSolicitacao.DOC_ENVIADO);
			}else {
				dadosIniciais.setStatus(StatusSolicitacao.PREENCHIDO);
				
			}

		}

		dadosIniciaisService.salvar(dadosIniciais);
		
		attributes.addFlashAttribute("id", dadosIniciais.getId());
		return new ModelAndView("redirect:/finalizar");
		
	}
	
	
	@RequestMapping("/gravar-sem-finalizar")
	public ModelAndView gravarSemFinalizar(RedirectAttributes attributes) {
		
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		if (concluido(dadosIniciais)) {
			if (dadosIniciais.getStatus() == StatusSolicitacao.PENDENCIA_DOCUMENTOS) {
				dadosIniciais.setStatus(StatusSolicitacao.DOC_ENVIADO);
			}else {
				dadosIniciais.setStatus(StatusSolicitacao.PREENCHIDO);
				
			}

		}

		dadosIniciaisService.salvar(dadosIniciais);
		
		attributes.addFlashAttribute("id", dadosIniciais.getId());
		return new ModelAndView("redirect:/finalizar");
		
	}	
	

	
	public boolean concluido(DadosIniciais dadosIniciais) {
		boolean concluido = false;

		if (dadosIniciais.getEtapa1() 
			&& dadosIniciais.getEtapa2()
			&& dadosIniciais.getEtapa3()
			&& dadosIniciais.getEtapa4()
			&& dadosIniciais.getEtapa5()
			&& dadosIniciais.getEtapa6()
			&& dadosIniciais.getEtapa7()
			&& dadosIniciais.getEtapa8()
			&& dadosIniciais.getEtapa9()
			&& dadosIniciais.getEtapa10()
			&& dadosIniciais.getEtapa11()
			&& dadosIniciais.getEtapa12()
			&& dadosIniciais.getEtapa13()
			&& dadosIniciais.getEtapa14()) {
				concluido =  true;
		}
		
		return concluido;

	}
	
}

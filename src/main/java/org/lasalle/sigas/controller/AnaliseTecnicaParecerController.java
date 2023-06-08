package org.lasalle.sigas.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.lasalle.sigas.model.AnaliseTecnicaFinanceira;
import org.lasalle.sigas.model.AnaliseTecnicaParecer;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.repository.AnaliseTecnicaFinanceiraRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.service.AnaliseTecnicaParecerService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/analise-tecnica-parecer")
public class AnaliseTecnicaParecerController {
	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	AnaliseTecnicaParecerService analiseTecnicaParecerService;
	
	@Autowired
	AnaliseTecnicaFinanceiraRepository analiseTecnicaFinanceiraRepository;


	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@Valid @RequestBody AnaliseTecnicaParecer analiseTecnicaParecer, BindingResult result){

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Informar todos os dados"); 
		}
				
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(Long.valueOf(analiseTecnicaParecer.getDadosIniciais()));
		dadosIniciais.setStatus(StatusSolicitacao.ANALISADO);
		
		Optional<AnaliseTecnicaFinanceira> analiseTecnicaFinanceira = analiseTecnicaFinanceiraRepository.findById(dadosIniciais.getId());
		
		if (!analiseTecnicaFinanceira.isPresent()) {
			return ResponseEntity.badRequest().body("Atenção: Não foi possível salvar o parecer. É necessário realizar a Análise Técnica Financeira primeiro."); 
		}
		
		
		dadosIniciaisService.salvar(dadosIniciais);
		
		analiseTecnicaParecerService.salvar(analiseTecnicaParecer);

		return ResponseEntity.ok().body("Analise parecer salva com sucesso");

	}
	
	
}

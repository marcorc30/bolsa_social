package org.lasalle.sigas.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.lasalle.sigas.controller.converter.PercentualParecerConverter;
import org.lasalle.sigas.dto.AnaliseTecnicaFinanceiraDTO;
import org.lasalle.sigas.model.AnaliseTecnicaFinanceira;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.PercentualParecer;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.PercentualParecerRepository;
import org.lasalle.sigas.service.AnaliseTecnicaFinanceiraService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/analise-tecnica-financeira")
public class AnaliseTecnicaFinanceiraController {

	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	AnaliseTecnicaFinanceiraService analiseTecnicaFinanceiraService;
	
	@Autowired
	PercentualParecerRepository percentualParecerRepository;


	@Autowired
	ModelMapper modelMapper;

	
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE } )
	public @ResponseBody ResponseEntity<?> salvar(	
													@RequestBody AnaliseTecnicaFinanceiraDTO analiseTecnicaFinanceiraJSON
													) throws JsonParseException, JsonMappingException, IOException{
		
		
		AnaliseTecnicaFinanceira atf = modelMapper.map(analiseTecnicaFinanceiraJSON, AnaliseTecnicaFinanceira.class);
		
		
		DadosIniciais dadosIniciaisObtido = dadosIniciaisRepository.findById(Long.valueOf(atf.getDadosIniciais()));
//		dadosIniciaisObtido.setStatus(StatusSolicitacao.ANALISADO);
		dadosIniciaisService.salvar(dadosIniciaisObtido);
		
		analiseTecnicaFinanceiraService.salvar(atf);

		return ResponseEntity.ok().body("Analise financeira salva com sucesso");

		
		
	}
		

}

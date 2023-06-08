package org.lasalle.sigas.controller;

import javax.validation.Valid;

import org.lasalle.sigas.model.AnaliseTecnicaFinanceira;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/analise-tecnica-financeira2")
public class AnaliseTecnicaFinanceiraController2 {

	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE } )
	public @ResponseBody ResponseEntity<?> salvar(@Valid @RequestBody AnaliseTecnicaFinanceira analiseTecnicaFinanceira, BindingResult result ){

		System.out.println("entrou aqui");
		
		return ResponseEntity.ok().body("Deu certo");
	}
	
	
}

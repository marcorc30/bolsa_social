package org.lasalle.sigas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InformacaoController {

	@RequestMapping("/")
	public String informacao() {
		return "informacoes/informacao";
	}
	
}

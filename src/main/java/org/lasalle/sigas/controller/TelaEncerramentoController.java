package org.lasalle.sigas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("finalizar")
public class TelaEncerramentoController {

	@RequestMapping(method = RequestMethod.GET)
	public String encerrar() {
		return "encerrar/EncerramentoProcesso";
	}
	
}

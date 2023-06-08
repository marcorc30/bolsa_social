package org.lasalle.sigas.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(@AuthenticationPrincipal User user) {

		if (user != null) {
			return "redirect:/";
			
		} 
		
		return "/Login";
	}
	
	@RequestMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	
	@RequestMapping("/sucesso")
	public String senhaAlterada() {
		return "Sucesso";
	}

	@RequestMapping("/alterar-senha-login")
	public String alterarSenha() {
		return "AlteraSenhaLogin";
	}

	
	
	@RequestMapping("/404")
	public String paginaNaoEncontrada() {
		return "404";
	}
	
	@RequestMapping("/warning")
	public String unidadeNaoEncontrada() {
		return "ErroProcesso";
	}	
	
	@RequestMapping("/erro-funcionario")
	public String funcionariosNaoEncontrados() {
		return "ErroFuncionario";
	}	
	
	@RequestMapping("/etapa-incorreta")
	public String etapaIncorreta() {
		return "EtapaInvalida";
	}	

	@RequestMapping("/acesso-nao-autorizado")
	public String acessoNaoAutorizado() {
		return "AcessoIndevido";
	}	
	
	
	@RequestMapping("/500")
	public String erroNoServidor() {
		return "500";
	}
	
	
}

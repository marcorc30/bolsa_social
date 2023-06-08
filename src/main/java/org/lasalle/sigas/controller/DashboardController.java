package org.lasalle.sigas.controller;

import java.util.ArrayList;
import java.util.List;

import org.lasalle.sigas.dto.DashboardDTO;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.DetalhesProcessoSeletivoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.security.UsuarioSistema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	@Autowired
	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@GetMapping("/dashboard")
	public ModelAndView index(@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		ModelAndView mv = new ModelAndView("dashboard/dashboard");
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		
		mv.addObject("anos", detalhesProcessoSeletivoRepository.anos());
		mv.addObject("unidades", unidades);
		
		
		return mv;
	}
	
	
	@PostMapping("/dashboard")
	public ModelAndView mostrarValores(String ano, String unidade, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		ModelAndView mv = new ModelAndView("dashboard/dashboard");

		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());

		List<Long> ids = new ArrayList<Long>();
		
		if (StringUtils.isEmpty(ano) || StringUtils.isEmpty(unidade)) {
			return index(usuarioSistema);
		}

		if (unidade.equals("0")) {
			System.out.println("entrou *********************");
			for (Unidade unidadeLoop : unidades) {
				ids.add(unidadeLoop.getId());
			}
		}else {
			ids.add(Long.valueOf(unidade));
		}
		
		
		mv.addObject("anos", detalhesProcessoSeletivoRepository.anos());
		mv.addObject("unidades", unidades);
		mv.addObject("totalInscricoes", detalhesProcessoSeletivoRepository.totalInscricoes(Integer.valueOf(ano), ids));
		mv.addObject("total100", detalhesProcessoSeletivoRepository.total100(Integer.valueOf(ano), ids));
		mv.addObject("total50", detalhesProcessoSeletivoRepository.total50(Integer.valueOf(ano), ids));
		mv.addObject("totalGratuidade", detalhesProcessoSeletivoRepository.totalGratuidade(Integer.valueOf(ano), ids));
		
		
		return mv;
	}
	
	
}

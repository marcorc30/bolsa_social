package org.lasalle.sigas.controller;

import java.util.List;

import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.security.UsuarioSistema;
import org.lasalle.sigas.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/unidades")
public class UnidadeController {
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@Autowired
	UnidadeService unidadeService;
	
	
	@RequestMapping("/listarUFS")
	public ModelAndView dadosIniciais() {
		
		ModelAndView mv = new ModelAndView("candidato/DadosIniciais");
//		List<Unidade> listaUnidades = unidadeRepository.findAll();
//		Set<String> ufs = new HashSet();
//		
//		for (Unidade unidade : listaUnidades) {
//			ufs.add(unidade.getUf());
//		}
//		
//		List<String> ufsString = new ArrayList<String>(ufs);
//		
//		Collections.sort(ufsString);
		
		
		mv.addObject("lista", unidadeService.listarUfs());
	//	mv.addObject("unidades", listaUnidades);
	
		return mv;
	}	

	
	
	@RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> unidadesPorUF(String uf){
		
		List<Unidade> unidades = unidadeRepository.findByUf(uf);
		return ResponseEntity.ok(unidades); 
		
	}
	
	@RequestMapping(value="/uf",  method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> unidadesPorUFAndIdUsuario(String uf, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuarioAndUf(usuarioSistema.getUsuario().getId(), uf); 
		
		System.out.println("Quantidade de unidades " + unidades.size());
		
		unidades.forEach((unidade)-> System.out.println(unidade.getNome()));
		
		return ResponseEntity.ok(unidades);
		
	}	

}

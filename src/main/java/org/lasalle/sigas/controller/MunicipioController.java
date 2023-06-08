package org.lasalle.sigas.controller;

import java.util.List;

import org.lasalle.sigas.model.Municipio;
import org.lasalle.sigas.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {
	
	@Autowired
	MunicipioRepository municipioRepository;

	@RequestMapping(value = "/{uf}", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarMunicipioPorEstado(@PathVariable("uf") String uf){
		
		List<Municipio> municipios = municipioRepository.municipioPorEstado(uf);
		
		return ResponseEntity.ok().body(municipios);
	}
	
}

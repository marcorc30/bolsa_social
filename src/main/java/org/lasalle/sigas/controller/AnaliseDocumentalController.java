package org.lasalle.sigas.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.lasalle.sigas.mail.Mailer;
import org.lasalle.sigas.model.AnaliseDocumental;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DocumentoRepository;
import org.lasalle.sigas.service.AnaliseDocumentalService;
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
@RequestMapping("/analise-documental")
public class AnaliseDocumentalController {
	
	@Autowired
	Mailer mailer;
	 
	@Autowired
	AnaliseDocumentalService analiseDocumentalService;

	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvarEnviarEmail(@Valid @RequestBody String json,
															BindingResult result) {
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Informar todos os dados"); 
		}
		AnaliseDocumental analiseDocumental = new AnaliseDocumental();
		
		JSONObject jsonObject;
		JSONParser parser = new JSONParser();

		
		try {
			
			jsonObject = (JSONObject) parser.parse(json);
			String descricao = (String)jsonObject.get("descricao");
			String titulo = (String)jsonObject.get("titulo");
			String data = (String) jsonObject.get("data");
			Integer dadosIniciaisJson = Integer.valueOf((String)jsonObject.get("dadosIniciais"));
			
			String[] dataSplit = data.split("-");
			
			analiseDocumental.setDescricao(descricao);
			analiseDocumental.setTitulo(titulo);
			analiseDocumental.setData(LocalDate.of(Integer.valueOf(dataSplit[0]), Integer.valueOf(dataSplit[1]), Integer.valueOf(dataSplit[2])));
			analiseDocumental.setDadosIniciais(dadosIniciaisJson);
			
			DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(Long.valueOf(analiseDocumental.getDadosIniciais()));
			dadosIniciais.setStatus(StatusSolicitacao.PENDENCIA_DOCUMENTOS);
			dadosIniciais.setEtapa14(false);
			dadosIniciaisService.salvar(dadosIniciais);
			
			analiseDocumentalService.salvar(analiseDocumental);
	
			
			mailer.enviar(analiseDocumental, dadosIniciais.getUsuario().getEmail());
			
			
		}catch(ParseException e) {
			return ResponseEntity.badRequest().body("Informar todos os dados"); 
		}
		
		
		return ResponseEntity.ok().body("Email enviado com sucesso");
		
	}

	
	
	
}

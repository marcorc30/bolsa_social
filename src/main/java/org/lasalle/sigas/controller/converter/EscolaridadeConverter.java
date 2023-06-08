package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Escolaridade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class EscolaridadeConverter implements Converter<String, Escolaridade>{

	@Override
	public Escolaridade convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Escolaridade escolaridade = new Escolaridade();
			escolaridade.setId(Long.valueOf(id));
			return escolaridade;
		}
		return null;
	}

	
	
}

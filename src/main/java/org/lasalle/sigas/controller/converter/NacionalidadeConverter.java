package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Nacionalidade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class NacionalidadeConverter implements Converter<String, Nacionalidade>{

	@Override
	public Nacionalidade convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Nacionalidade nacionalidade = new Nacionalidade();
			nacionalidade.setId(Long.valueOf(id));
			return nacionalidade;
		}
		
		return null;
	}

}

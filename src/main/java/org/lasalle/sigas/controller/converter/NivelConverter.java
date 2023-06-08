package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Nivel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class NivelConverter implements Converter<String, Nivel>{

	@Override
	public Nivel convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Nivel nivel = new Nivel();
			nivel.setId(Long.valueOf(id));
			return nivel;
		}
		
		return null;
	}

}

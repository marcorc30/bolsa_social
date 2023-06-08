package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.MoradiaCondicao;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MoradiaCondicaoConverter implements Converter<String, MoradiaCondicao>{

	@Override
	public MoradiaCondicao convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			MoradiaCondicao moradia = new MoradiaCondicao();
			moradia.setId(Long.valueOf(id));
			return moradia;
		}
		
		return null;
	}

	
	
}

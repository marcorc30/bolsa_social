package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.MoradiaImovel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MoradiaImovelConverter implements Converter<String, MoradiaImovel>{

	@Override
	public MoradiaImovel convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			MoradiaImovel moradia = new MoradiaImovel();
			moradia.setId(Long.valueOf(id));
			return moradia;
		}
		
		return null;
	}

	
	
}

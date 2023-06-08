package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.MoradiaArea;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MoradiaAreaConverter implements Converter<String, MoradiaArea>{

	@Override
	public MoradiaArea convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			MoradiaArea moradia = new MoradiaArea();
			moradia.setId(Long.valueOf(id));
			return moradia;
		}
		
		return null;
	}

}

package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Parentesco;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class ParentescoConverter implements Converter<String, Parentesco>{

	@Override
	public Parentesco convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Parentesco parentesco = new Parentesco();
			parentesco.setId(Long.valueOf(id));
			return parentesco;
		}
		
		return null;
	}

}

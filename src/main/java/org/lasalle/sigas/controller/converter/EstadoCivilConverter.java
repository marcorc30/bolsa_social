package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.EstadoCivil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class EstadoCivilConverter implements Converter<String, EstadoCivil>{

	@Override
	public EstadoCivil convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			EstadoCivil estadoCivil = new EstadoCivil();
			estadoCivil.setId(Long.valueOf(id));
			return estadoCivil;
		}
		return null;
	}

}

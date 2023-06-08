package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.MoradiaArea;
import org.lasalle.sigas.model.MoradiaTipo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MoradiaTipoConverter implements Converter<String, MoradiaTipo>{

	@Override
	public MoradiaTipo convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			MoradiaTipo moradia = new MoradiaTipo();
			moradia.setId(Long.valueOf(id));
			return moradia;
		}
		
		return null;
	}

	
}

package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Turno;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TurnoConverter implements Converter<String, Turno>{

	@Override
	public Turno convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			Turno turno = new Turno();
			turno.setId(Long.valueOf(id));
			return turno;
		}
		return null;
	}
	
	

}

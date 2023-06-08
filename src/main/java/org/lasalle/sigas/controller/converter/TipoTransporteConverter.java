package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoTransporte;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoTransporteConverter implements Converter<String, TipoTransporte>{

	@Override
	public TipoTransporte convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			TipoTransporte tipo = new TipoTransporte();
			tipo.setId(Long.valueOf(id));
			return tipo;
		}
		
		return null;
	}

}

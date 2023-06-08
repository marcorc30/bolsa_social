package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoRenda;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoRendaConverter implements Converter<String, TipoRenda>{

	@Override
	public TipoRenda convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			TipoRenda tipoRenda = new TipoRenda();
			tipoRenda.setId(Long.valueOf(id));
			return tipoRenda;
		}
		return null;
	}

}

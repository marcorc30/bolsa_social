package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoProcessoSeletivo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoProcessoSeletivoConverter implements Converter<String, TipoProcessoSeletivo>{

	@Override
	public TipoProcessoSeletivo convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			TipoProcessoSeletivo tipoProcessoSeletivo = new TipoProcessoSeletivo();
			tipoProcessoSeletivo.setId(Long.valueOf(id));
			return tipoProcessoSeletivo;
			
		}
		else
			return null;
		
	}

}

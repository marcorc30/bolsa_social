package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoInstituicao;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoInstituicaoConverter implements Converter<String, TipoInstituicao>{

	@Override
	public TipoInstituicao convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			TipoInstituicao tipo = new TipoInstituicao();
			tipo.setId(Long.valueOf(id));
			return tipo;
		}
		
		return null;
	}

}

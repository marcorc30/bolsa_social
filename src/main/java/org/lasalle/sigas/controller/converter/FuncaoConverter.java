package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Funcao;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class FuncaoConverter implements Converter<String, Funcao> {

	@Override
	public Funcao convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Funcao funcao = new Funcao();
			funcao.setId(Long.valueOf(id));
			return funcao;
		}

		return null;
	}

}

package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Unidade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class UnidadeConverter implements Converter<String, Unidade>{

	@Override
	public Unidade convert(String id) {
		
		
		if (!StringUtils.isEmpty(id)){
			Unidade unidade = new Unidade();
			unidade.setId(Long.valueOf(id));
			return unidade;
		}
		return null;
	}

}

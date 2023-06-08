package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class ComposicaoFamiliarConverter implements Converter<String, ComposicaoFamiliar>{

	@Override
	public ComposicaoFamiliar convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			ComposicaoFamiliar composicaoFamiliar = new ComposicaoFamiliar();
			composicaoFamiliar.setId(Long.valueOf(id));
			return composicaoFamiliar;
			
		}
		return null;
	}

}

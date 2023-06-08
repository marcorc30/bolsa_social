package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.PercentualParecer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class PercentualParecerConverter implements Converter<String, PercentualParecer>{

	@Override
	public PercentualParecer convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			PercentualParecer percentualParecer = new PercentualParecer();
			percentualParecer.setId(Long.valueOf(id));
			return percentualParecer;
		}
		
		return null;
	}

}

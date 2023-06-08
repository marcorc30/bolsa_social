package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.NumeroEdital;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class NumeroEditalConverter implements Converter<String, NumeroEdital>{

	@Override
	public NumeroEdital convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			NumeroEdital numeroEdital = new NumeroEdital();
			numeroEdital.setId(Long.valueOf(id));
			return numeroEdital;
		}
		
		return null;
	}

}

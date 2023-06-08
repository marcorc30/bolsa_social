package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Serie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class SerieConverter implements Converter<String, Serie>{

	@Override
	public Serie convert(String id) {
		if (!StringUtils.isEmpty(id)){
			Serie serie = new Serie();
			serie.setId(Long.valueOf(id));
			return serie;
		}
		
		return null;
	}

}

package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.MoradiaResposta;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MoradiaRespostaConverter implements Converter<String, MoradiaResposta>{

	@Override
	public MoradiaResposta convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			MoradiaResposta moradia = new MoradiaResposta();
			moradia.setId(Long.valueOf(id));
			return moradia;
		}
		
		return null;
	}

	
	
}

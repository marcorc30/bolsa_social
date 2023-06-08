package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoProgramaGovernamental;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoProgramaGovernamentalConverter implements Converter<String, TipoProgramaGovernamental>{

	@Override
	public TipoProgramaGovernamental convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			TipoProgramaGovernamental tipoProgramaGovernamental = new TipoProgramaGovernamental();
			tipoProgramaGovernamental.setId(Long.valueOf(id));
			return tipoProgramaGovernamental;
		}
		return null;
	}

}

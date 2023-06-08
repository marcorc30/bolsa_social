package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.SituacaoReponsavel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class SituacaoResponsavelConverter implements Converter<String, SituacaoReponsavel>{

	@Override
	public SituacaoReponsavel convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			SituacaoReponsavel situacaoResponsavel = new SituacaoReponsavel();
			situacaoResponsavel.setId(Long.valueOf(id));
			return situacaoResponsavel;
		}
		
		return null;
	}

}

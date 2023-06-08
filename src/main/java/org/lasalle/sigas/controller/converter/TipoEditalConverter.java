package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoEdital;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoEditalConverter implements Converter<String, TipoEdital>{

	@Override
	public TipoEdital convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			TipoEdital tipoEdital = new TipoEdital();
			tipoEdital.setId(Long.valueOf(id));
			return tipoEdital;
		}
		return null;
	}

}

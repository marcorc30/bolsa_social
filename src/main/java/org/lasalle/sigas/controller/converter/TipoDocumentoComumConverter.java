package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoDocumentoComum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoDocumentoComumConverter implements Converter<String, TipoDocumentoComum>{

	@Override
	public TipoDocumentoComum convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			TipoDocumentoComum tipoDocumentoComum = new TipoDocumentoComum();
			tipoDocumentoComum.setId(Long.valueOf(id));
			return tipoDocumentoComum;
		}
		return null;
	}

}
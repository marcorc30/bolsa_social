package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.TipoDespesa;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class TipoDespesaConverter implements Converter<String, TipoDespesa>{

	@Override
	public TipoDespesa convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			TipoDespesa tipoDespesa = new TipoDespesa();
			tipoDespesa.setId(Long.valueOf(id));
			return tipoDespesa;
		}
		
		return null;
	}

}

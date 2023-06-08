package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.BolsaAnteriorReposta;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class BolsaAnteriorRespostaConverter implements Converter<String, BolsaAnteriorReposta> {

	@Override
	public BolsaAnteriorReposta convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			BolsaAnteriorReposta bolsaAnteriorResposta = new BolsaAnteriorReposta();
			bolsaAnteriorResposta.setId(Long.valueOf(id));
			return bolsaAnteriorResposta;
		}
		return null;
	}

}

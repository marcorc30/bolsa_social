package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.IrReposta;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class IrRespostaConverter implements Converter<String, IrReposta>{

	@Override
	public IrReposta convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			IrReposta irResposta = new IrReposta();
			irResposta.setId(Long.valueOf(id));
			return irResposta;
		}
		
		return null;
	}

}

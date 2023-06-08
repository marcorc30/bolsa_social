package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DetalheProcessoSeletivoConverter implements Converter<String, DetalhesProcessoSeletivo>{

	@Override
	public DetalhesProcessoSeletivo convert(String id) {
		
		System.out.println("Passando no converter detalheProcessoSeletivo" + id );
		
		if (!StringUtils.isEmpty(id)) {
			DetalhesProcessoSeletivo detalhes = new DetalhesProcessoSeletivo();
			detalhes.setId(Long.valueOf(id));
			return detalhes;
		}
		
		return null;
		
	}

}

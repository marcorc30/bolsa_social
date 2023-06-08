package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.MotivoAlteracao;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class MotivoAlteracaoConverter implements Converter<String, MotivoAlteracao>{

	@Override
	public MotivoAlteracao convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			MotivoAlteracao motivoAlteracao = new MotivoAlteracao();
			motivoAlteracao.setId(Long.valueOf(id));
			return motivoAlteracao;
		}
		
		return null;
	}

}

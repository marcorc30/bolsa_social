package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Funcionario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class FuncionarioConverter implements Converter<String, Funcionario>{

	@Override
	public Funcionario convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Funcionario funcionario = new Funcionario();
			funcionario.setId(Long.valueOf(id));
			return funcionario;
		}
		return null;
	}

}

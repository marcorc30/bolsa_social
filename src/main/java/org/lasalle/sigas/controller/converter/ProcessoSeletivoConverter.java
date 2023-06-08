package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.ProcessoSeletivo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class ProcessoSeletivoConverter implements Converter<String, ProcessoSeletivo>{

	@Override
	public ProcessoSeletivo convert(String id) {
		System.out.println("Passando no converter Processo Seletivo" + id );
		if (!StringUtils.isEmpty(id)) {
			ProcessoSeletivo processoSeletivo = new ProcessoSeletivo();
			processoSeletivo.setId(Long.valueOf(id));
			return processoSeletivo;
		}
		
		return null;
	}

	

}

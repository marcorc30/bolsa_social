package org.lasalle.sigas.controller.converter;

import org.lasalle.sigas.model.Usuario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class UsuarioConverter implements Converter<String, Usuario>{

	@Override
	public Usuario convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Usuario usuario = new Usuario();
			usuario.setId(Long.valueOf(id));
			return usuario;
		}
		
		return null;
	}

}

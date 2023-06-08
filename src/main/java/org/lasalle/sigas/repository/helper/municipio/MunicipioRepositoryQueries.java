package org.lasalle.sigas.repository.helper.municipio;

import java.util.List;

import org.lasalle.sigas.model.Municipio;

public interface MunicipioRepositoryQueries {

	public List<Municipio> municipioPorEstado(String uf);
}

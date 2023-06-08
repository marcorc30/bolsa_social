package org.lasalle.sigas.repository.helper.serie;

import java.util.List;

import org.lasalle.sigas.model.Serie;

public interface SerieRepositoryQueries {
	
	public List<Serie> seriesPorUnidade(Long idUnidade);

}

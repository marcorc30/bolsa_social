package org.lasalle.sigas.repository;


import org.lasalle.sigas.model.Serie;
import org.lasalle.sigas.repository.helper.serie.SerieRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long>, SerieRepositoryQueries{
	
}

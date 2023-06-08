package org.lasalle.sigas.repository;

import org.lasalle.sigas.model.Municipio;
import org.lasalle.sigas.repository.helper.municipio.MunicipioRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>, MunicipioRepositoryQueries{

}

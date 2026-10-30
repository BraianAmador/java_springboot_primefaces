
package com.relesys.organizacion.repository;

import com.relesys.organizacion.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Integer> {

    List<Sector> findByEmpresaId(Integer empresaId);

}
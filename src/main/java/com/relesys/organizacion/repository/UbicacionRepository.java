
package com.relesys.organizacion.repository;

import com.relesys.organizacion.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {

    List<Ubicacion> findByEmpresaId(Integer empresaId);

}

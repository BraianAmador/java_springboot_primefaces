
package com.relesys.organizacion.repository;

import com.relesys.organizacion.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}


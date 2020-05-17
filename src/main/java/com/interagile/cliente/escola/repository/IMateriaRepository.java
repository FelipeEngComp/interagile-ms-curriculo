package com.interagile.cliente.escola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.interagile.cliente.escola.model.MateriaDB;

public interface IMateriaRepository extends JpaRepository<MateriaDB, Long> {

	@Query("select m from MateriaDB m where m.codigo =:codigo")
	MateriaDB findMateriaByCodigo(@Param("codigo")String codigo);

}
